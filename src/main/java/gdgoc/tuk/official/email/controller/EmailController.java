package gdgoc.tuk.official.email.controller;

import gdgoc.tuk.official.email.dto.EmailVerificationRequest;
import gdgoc.tuk.official.google.service.GmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final GmailService gmailService;

    @PostMapping
    public void verifyTukEmail(@RequestBody EmailVerificationRequest request){
        
    }
}
