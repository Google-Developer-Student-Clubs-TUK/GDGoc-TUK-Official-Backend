package gdgoc.tuk.official.global.config.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class GoogleApiConfig {

  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final List<String> SCOPES =
      List.of(SheetsScopes.SPREADSHEETS, GmailScopes.GMAIL_SEND);

  @Value("${google.spread-sheets.application-name}")
  private String applicationName;

  @Value("${google.spread-sheets.token-path}")
  private String tokensDirectoryPath;

  @Value("${google.spread-sheets.credential-path}")
  private String credentialsPath;

  private Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
    InputStream in = new ClassPathResource(credentialsPath).getInputStream();
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    GoogleAuthorizationCodeFlow flow =
        new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensDirectoryPath)))
            .setAccessType("offline")
            .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  @Bean
  public Sheets sheets() throws GeneralSecurityException, IOException {
    final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    return new Sheets.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
        .setApplicationName(applicationName)
        .build();
  }

  @Bean
  public Gmail gmail() throws IOException {
    final NetHttpTransport httpTransport = new NetHttpTransport();
    return new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
        .setApplicationName(applicationName)
        .build();
  }
}
