package gdgoc.tuk.official.google.service;

public interface EmailSender {
    void send(String to, String subject, String content);
}
