package gdgoc.tuk.official.google.initializer;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
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

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.exception.SheetsCreationException;
import gdgoc.tuk.official.google.service.SpreadSheetsService;

import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.dto.SpreadSheetInformation;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpreadSheetsInitializer {

    private static final String SPREAD_SHEET_TITLE = "GDGOC-TUK %s년도 지원자 응답";
    private static final int INITIAL_SHEET_ID = 0;
    @Value("${google.leader-gmail}")
    private String LEADER_GMAIL;
    private final Sheets sheetsClient;
    private final Drive driveClient;
    private final SpreadSheetsService spreadSheetsService;
    private final RecruitmentRepository recruitmentRepository;


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

    public SpreadSheetInformation init(final String generation, final List<List<Object>> questions) {
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
        return new SpreadSheetInformation(spreadsheetId,result.getSpreadsheetUrl());
    }

    private Spreadsheet createSpreadSheets(final Spreadsheet spreadsheet) {
        final Spreadsheet result;
        try {
            result = sheetsClient.spreadsheets().create(spreadsheet).execute();
            Permission permission = new Permission();
            permission.setType("user").setRole("writer").setEmailAddress(LEADER_GMAIL);
            driveClient.permissions().create(result.getSpreadsheetId(),permission).setFields("id").execute();

            log.info("createSpreadSheets:{}",result);
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
}
