package gdgoc.tuk.official.config;


import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;

import gdgoc.tuk.official.email.service.TestEmailSender;
import gdgoc.tuk.official.google.service.EmailSender;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public Sheets testSheets() {
        return Mockito.mock(Sheets.class);
    }

    @Bean
    public Drive testDrive() {
        return Mockito.mock(Drive.class);
    }

//    @Bean
//    public EmailSender emailSender(){
//        return new TestEmailSender();
//    }
}
