package gdgoc.tuk.official.google.initializer;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BasicFilter;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.Border;
import com.google.api.services.sheets.v4.model.Color;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.SetBasicFilterRequest;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateBordersRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.exception.SheetsCreationException;
import gdgoc.tuk.official.google.service.SpreadSheetsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpreadSheetsInitializer {

    private static final String SPREAD_SHEET_TITLE = "GDGOC-TUK %s기 지원자 응답";
    private static final int INITIAL_SHEET_ID = 0;
    private static final String POSITION = "시트1!1:1";
    private final Sheets sheetsClient;
    private final SpreadSheetsService spreadSheetsService;

    private Border getBorder() {
        return new Border()
                .setStyle("SOLID")
                .setWidth(10)
                .setColor(new Color().setRed(0.6f).setGreen(0.6f).setBlue(0.6f));
    }

    private GridRange getRange() {
        return new GridRange().setSheetId(INITIAL_SHEET_ID).setStartRowIndex(0).setEndRowIndex(1);
    }

    private void setHeaderRow(String spreadsheetId) {
        BatchUpdateSpreadsheetRequest request =
                new BatchUpdateSpreadsheetRequest()
                        .setRequests(
                                Collections.singletonList(
                                        new Request()
                                                .setSetBasicFilter(
                                                        new SetBasicFilterRequest()
                                                                .setFilter(
                                                                        new BasicFilter()
                                                                                .setRange(
                                                                                        new GridRange()
                                                                                                .setSheetId(
                                                                                                        INITIAL_SHEET_ID)
                                                                                                .setStartRowIndex(
                                                                                                        0)
                                                                                                .setEndRowIndex(
                                                                                                        1))))));

        try {
            sheetsClient.spreadsheets().batchUpdate(spreadsheetId, request).execute();
        } catch (IOException e) {
            log.error("Filter Creation Error : {}", e.getLocalizedMessage());
        }
    }

    public String init(final String generation, final List<List<Object>> questions) {
        final Spreadsheet spreadsheet =
                new Spreadsheet()
                        .setProperties(
                                new SpreadsheetProperties()
                                        .setTitle(SPREAD_SHEET_TITLE.formatted(generation)));
        final Spreadsheet result = createSpreadSheets(spreadsheet);
        final String spreadsheetId = result.getSpreadsheetId();
        spreadSheetsService.write(spreadsheetId, questions, generation);
        setHeaderBottomBorder(spreadsheetId);
        setHeaderRow(spreadsheetId);
        return spreadsheetId;
    }

    private Spreadsheet createSpreadSheets(final Spreadsheet spreadsheet) {
        final Spreadsheet result;
        try {
            result = sheetsClient.spreadsheets().create(spreadsheet).execute();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            throw new SheetsCreationException(ErrorCode.SHEETS_CREATION_FAILED);
        }
        return result;
    }

    private void setHeaderBottomBorder(String spreadsheetId) {
        Border bottomBorder = getBorder();
        Request borderRequest =
                new Request()
                        .setUpdateBorders(
                                new UpdateBordersRequest()
                                        .setRange(getRange())
                                        .setBottom(bottomBorder));
        BatchUpdateSpreadsheetRequest request =
                new BatchUpdateSpreadsheetRequest()
                        .setRequests(Collections.singletonList(borderRequest));
        try {
            sheetsClient.spreadsheets().batchUpdate(spreadsheetId, request).execute();
        } catch (IOException e) {
            log.error("Bottom Border Creation Error : {}", e.getLocalizedMessage());
        }
    }

    private UpdateValuesResponse setUpQuestions(String spreadsheetId, List<List<Object>> values) {
        UpdateValuesResponse result = null;
        try {
            // Updates the values in the specified range.
            ValueRange body = new ValueRange().setValues(values).setMajorDimension("ROWS");
            result =
                    sheetsClient
                            .spreadsheets()
                            .values()
                            .update(spreadsheetId, POSITION, body)
                            .setValueInputOption("USER_ENTERED")
                            .execute();
            log.info("%d cells updated.", result.getUpdatedCells());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                log.error("Spreadsheet not found with questionId '%s'.\n", spreadsheetId);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
