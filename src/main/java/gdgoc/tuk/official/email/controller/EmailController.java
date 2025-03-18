package gdgoc.tuk.official.email.controller;

import gdgoc.tuk.official.email.dto.EmailSendingRequest;
import gdgoc.tuk.official.email.dto.EmailVerificationRequest;
import gdgoc.tuk.official.email.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "이메일 API")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    @PreAuthorize("permitAll()")
    @Operation(summary = "인증코드 전송", description = "인증 코드를 전송합니다.")
    public void sendVerificationCode(@RequestBody EmailSendingRequest request)
            throws MessagingException, IOException {
        emailService.sendVerificationMail(request.email());
    }

    @PostMapping("/code")
    @PreAuthorize("permitAll()")
    @Operation(summary = "인증 코드 검증", description = "인증 코드를 검증합니다.")
    public void verify(@RequestBody EmailVerificationRequest request) {
        emailService.verifyEmail(request);
    }
}
