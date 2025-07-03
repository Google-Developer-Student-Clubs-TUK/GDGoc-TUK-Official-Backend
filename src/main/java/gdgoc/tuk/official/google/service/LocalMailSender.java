package gdgoc.tuk.official.google.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
@Slf4j
@RequiredArgsConstructor
public class LocalMailSender implements EmailSender {

    @Override
    @Async
    public void send(final String to, final String subject, final String content) {
        try {
            log.debug("[MOCK EMAIL START] to={}", to);
            Thread.sleep(3300);
            log.info("thread name : {}", Thread.currentThread().getName());
            log.debug("[MOCK EMAIL DONE] to={}", to);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Mock email sending interrupted", e);
        }
    }
}
