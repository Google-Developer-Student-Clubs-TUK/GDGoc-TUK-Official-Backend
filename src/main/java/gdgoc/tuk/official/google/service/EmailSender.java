package gdgoc.tuk.official.google.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailSender {
    void send(String to, String subject, String content);
}
