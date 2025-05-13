package gdgoc.tuk.official.global.config.google;

import static com.google.api.services.sheets.v4.SheetsScopes.DRIVE;
import static com.google.api.services.sheets.v4.SheetsScopes.DRIVE_FILE;
import static com.google.api.services.sheets.v4.SheetsScopes.all;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleApiConfig {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${google.spread-sheets.application-name}")
    private String applicationName;

    @Value("${google.spread-sheets.credential}")
    private String credentialsPath;

    @Bean
    public Sheets sheets() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(ServiceAccountCredentials.fromStream(
            new FileInputStream("src/main/resources/" + credentialsPath))
            .createScoped(SheetsScopes.SPREADSHEETS)))
                .setApplicationName(applicationName)
                .build();
    }

    @Bean
    public Drive drive() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(httpTransport, JSON_FACTORY,
            new HttpCredentialsAdapter(ServiceAccountCredentials.fromStream(
                new FileInputStream("src/main/resources/" + credentialsPath))
                .createScoped(List.of(DRIVE,DRIVE_FILE))))
            .setApplicationName(applicationName)
            .build();
    }
}
