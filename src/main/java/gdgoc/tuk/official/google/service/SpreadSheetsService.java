package gdgoc.tuk.official.google.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import gdgoc.tuk.official.answer.repository.NextAnswerRowRedisRepository;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpreadSheetsService {

    private static final String POSITION = "시트1!%s:%s";
    private final Sheets sheetsClient;
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final NextAnswerRowRedisRepository nextAnswerRowRedisRepository;

    public UpdateValuesResponse write(String spreadsheetId, List<List<Object>> values,
        String generation){
        UpdateValuesResponse result = null;
        try {
            result = updateSheets(spreadsheetId,values,generation);
            log.info("%d cells updated.", result.getUpdatedCells());
        } catch (GoogleJsonResponseException e) {
            log.error("update error %s",e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private UpdateValuesResponse updateSheets(final String spreadsheetId,
        List<List<Object>> values,String generation)
        throws IOException {
        final ValueRange body = new ValueRange().setValues(values).setMajorDimension("ROWS");
        UpdateValuesResponse response;
        reentrantLock.lock();
        String nextRow = (String) nextAnswerRowRedisRepository.findNextRow(generation);
        try{
            final String formattedPosition = POSITION.formatted(nextRow,nextRow);
            response = sheetsClient
                .spreadsheets()
                .values()
                .update(spreadsheetId, formattedPosition, body)
                .setValueInputOption("USER_ENTERED")
                .execute();
            nextAnswerRowRedisRepository.increaseNextRow(generation);
        }finally{
            reentrantLock.unlock();
        }
        return response;
    }
}
