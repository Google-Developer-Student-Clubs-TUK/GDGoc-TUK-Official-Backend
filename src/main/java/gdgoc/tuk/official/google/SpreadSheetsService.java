package gdgoc.tuk.official.google;

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
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpreadSheetsService {

    private static final String SPREAD_SHEET_TITLE = "GDGOC";
    private static final int INITIAL_SHEET_ID = 0;
    private final Sheets sheetsClient;

    private static Border getBorder() {
        Border bottomBorder = new Border()
            .setStyle("SOLID")
            .setWidth(10)
            .setColor(new Color()
                .setRed(0.6f)
                .setGreen(0.6f)
                .setBlue(0.6f)
            );
        return bottomBorder;
    }

    private static GridRange getRange() {
        return new GridRange()
            .setSheetId(INITIAL_SHEET_ID)
            .setStartRowIndex(0)
            .setEndRowIndex(1);
    }

    private void setHeaderRow(String spreadsheetId) throws IOException {

        BatchUpdateSpreadsheetRequest request = new BatchUpdateSpreadsheetRequest()
            .setRequests(Collections.singletonList(new Request()
                .setSetBasicFilter(new SetBasicFilterRequest()
                    .setFilter(new BasicFilter()
                        .setRange(new GridRange()
                            .setSheetId(INITIAL_SHEET_ID)
                            .setStartRowIndex(0)  // 첫 번째 행을 헤더로 사용
                            .setEndRowIndex(1)    // 헤더 행만 필터 적용
                        )))));

        sheetsClient.spreadsheets().batchUpdate(spreadsheetId, request).execute();
        log.info("Header row set with filter.");
    }

    public void createSpreadSheet(final String generation,final List<List<Object>> values) throws IOException {
        final Spreadsheet spreadsheet = new Spreadsheet()
            .setProperties(new SpreadsheetProperties().setTitle(SPREAD_SHEET_TITLE));
        final Spreadsheet result = sheetsClient.spreadsheets().create(spreadsheet).execute();
        final String spreadsheetId = result.getSpreadsheetId();
        setUpQuestions(spreadsheetId,values);
        setHeaderBottomBorder(spreadsheetId,SPREAD_SHEET_TITLE);
        setHeaderRow(spreadsheetId);
    }

    private void setHeaderBottomBorder(String spreadsheetId, String sheetName) throws IOException {
        Border bottomBorder = getBorder();

        // 요청 생성
        Request borderRequest = new Request()
            .setUpdateBorders(
                new UpdateBordersRequest()
            .setRange(getRange())
            .setBottom(bottomBorder)
        );

        BatchUpdateSpreadsheetRequest request = new BatchUpdateSpreadsheetRequest()
            .setRequests(Collections.singletonList(borderRequest));

        sheetsClient.spreadsheets().batchUpdate(spreadsheetId, request).execute();
    }

    private UpdateValuesResponse setUpQuestions(String spreadsheetId,
        List<List<Object>> values){
        UpdateValuesResponse result = null;
        try {
            // Updates the values in the specified range.
            ValueRange body = new ValueRange()
                .setValues(values)
                .setMajorDimension("ROWS");
            result = sheetsClient.spreadsheets().values().update(spreadsheetId,"시트1!1:1",body)
                .setValueInputOption("USER_ENTERED")
                .execute();
            log.info("%d cells updated.", result.getUpdatedCells());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                log.error("Spreadsheet not found with id '%s'.\n", spreadsheetId);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
