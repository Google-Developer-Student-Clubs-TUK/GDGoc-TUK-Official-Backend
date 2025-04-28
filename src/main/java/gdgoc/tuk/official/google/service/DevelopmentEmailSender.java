package gdgoc.tuk.official.google.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev","local"})
@Slf4j
public class DevelopmentEmailSender implements EmailSender {

    @Override
    public void send(final String to, final String subject, final String content) {
        log.info("Send To : {} , Title : {} , Cotent : {}",to,subject,content);
    }
}
