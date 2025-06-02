package gdgoc.tuk.official.google.service;

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
@Profile("local")
@Slf4j
@RequiredArgsConstructor
public class LocalMailSender implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(final String to, final String subject, final String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom("GDGoCTUK"); // service name
            helper.setTo(to); // customer email
            helper.setSubject(subject); // email title
            helper.setText(content, true); // content, html: true
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // 에러 출력
        }
    }
}
