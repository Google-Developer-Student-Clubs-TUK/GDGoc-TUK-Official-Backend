package gdgoc.tuk.official.google.service;


import io.github.bucket4j.Bucket;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("prod")
@Slf4j
public class GmailSender implements EmailSender {

    private final JavaMailSender javaMailSender;
    private final Bucket bucket;

    @Async
    @Override
    public void send(String to, String subject, String content) {
//        try{
//            bucket.asBlocking().consume(1);
//        }catch (InterruptedException e){
//            log.info("InterruptedException : {}",e.getLocalizedMessage());
//        }
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom("GDGoCTUK");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("MessagingException : {}", e.getLocalizedMessage());
        } catch (RuntimeException e) {
            log.error("Runtime Exception : {}", e.getLocalizedMessage());
        }
//        try {
//            log.debug("[MOCK EMAIL START] to={}", to);
//            Thread.sleep(3300);
//            log.debug("[MOCK EMAIL DONE] to={}", to);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            log.error("Mock email sending interrupted", e);
//        }
    }
}
