package gdgoc.tuk.official.email.service;

import gdgoc.tuk.official.google.service.EmailSender;
import java.util.ArrayList;
import java.util.List;

public class TestEmailSender implements EmailSender {

    private final List<SentEmail> sentEmails = new ArrayList<>();

    @Override
    public void send(String to, String subject, String content) {
        sentEmails.add(new SentEmail(to, subject, content));
    }

    public int sentCount() {
        return sentEmails.size();
    }

    public SentEmail lastSentEmail() {
        return sentEmails.get(sentEmails.size() - 1);
    }

    public static class SentEmail {

        private final String to;
        private final String subject;
        private final String content;

        public SentEmail(String to, String subject, String content) {
            this.to = to;
            this.subject = subject;
            this.content = content;
        }

        public String getTo() {
            return to;
        }

        public String getSubject() {
            return subject;
        }

        public String getContent() {
            return content;
        }
    }
}