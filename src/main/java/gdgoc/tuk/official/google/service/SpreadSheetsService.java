package gdgoc.tuk.official.google.service;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpreadSheetsService {

    private final Sheets sheetsClient;

    public UpdateValuesResponse write(String spreadsheetId, List<List<Object>> values, String position){
        UpdateValuesResponse result = null;
        try {
            // Updates the values in the specified range.
            ValueRange body = new ValueRange().setValues(values).setMajorDimension("ROWS");
            result =
                sheetsClient
                    .spreadsheets()
                    .values()
                    .update(spreadsheetId, position, body)
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
