package gdgoc.tuk.official.email.controller;

import gdgoc.tuk.official.email.dto.EmailSendingRequest;
import gdgoc.tuk.official.email.dto.EmailVerificationRequest;
import gdgoc.tuk.official.email.service.EmailService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.mail.MessagingException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public void sendVerificationCode(@RequestBody EmailSendingRequest request)
            throws MessagingException, IOException {
        emailService.sendVerificationMail(request.email());
    }

    @PostMapping("/code")
    @PreAuthorize("permitAll()")
    public void verify(@RequestBody EmailVerificationRequest request) {
        emailService.verifyEmail(request);
    }
}
