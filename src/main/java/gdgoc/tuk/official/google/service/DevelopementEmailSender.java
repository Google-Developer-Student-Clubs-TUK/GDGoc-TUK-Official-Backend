package gdgoc.tuk.official.google.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("dev")
@Slf4j
@EnableAsync
public class DevelopementEmailSender implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Async
    public void send(String to, String subject, String content) {
        try {
            Thread.sleep(350);
            log.info("Test");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
