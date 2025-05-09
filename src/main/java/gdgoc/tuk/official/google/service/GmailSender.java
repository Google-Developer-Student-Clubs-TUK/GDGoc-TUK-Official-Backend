package gdgoc.tuk.official.google.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Service
@RequiredArgsConstructor
@Profile("prod")
@Slf4j
public class GmailSender implements EmailSender {

    private final Gmail gmail;
    @Value("${google.gmail.address}")
    private String gdgocTukMailAddress;

    public void send(String to, String subject, String content) {
        MimeMessage email = createEmail(to, gdgocTukMailAddress, subject, content);
        sendMessage(email);
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyHtml) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        try{
            email.setFrom(new InternetAddress(from));
            email.addRecipient(RecipientType.TO, new InternetAddress(to));
            email.setSubject(subject, "UTF-8");
            email.setContent(bodyHtml, "text/html; charset=utf-8"); // HTML 형식 지정
        }catch (MessagingException e){
            log.error(e.getMessage());
        }
        return email;
    }

    private void sendMessage(MimeMessage email) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try{
            email.writeTo(buffer);
        }catch (IOException | MessagingException e ){
            log.error(e.getMessage());
        }
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        try{
            gmail.users().messages().send("me", message).execute();
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }
}
