package gdgoc.tuk.official.google.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import lombok.RequiredArgsConstructor;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Service
@RequiredArgsConstructor
public class GmailService {

    private final Gmail gmail;
    @Value("${google.gmail.address}")
    private String gdgocTukMailAddress;
    @Value("${google.gmail.title}")
    private String title;

    public void sendEmail(String to, String subject, String content)
            throws MessagingException, IOException {
        MimeMessage email = createEmail(to, gdgocTukMailAddress, subject, content);
        sendMessage(email);
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyHtml)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject, "UTF-8");
        email.setContent(bodyHtml, "text/html; charset=utf-8"); // HTML 형식 지정
        return email;
    }

    private void sendMessage(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        gmail.users().messages().send("me", message).execute();
    }
}
