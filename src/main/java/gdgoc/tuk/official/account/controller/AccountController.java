package gdgoc.tuk.official.account.controller;

import gdgoc.tuk.official.account.dto.RegisterRequest;
import gdgoc.tuk.official.account.dto.RegisterResponse;
import gdgoc.tuk.official.account.service.AccountRegisterService;
import gdgoc.tuk.official.account.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Tag(name = "계정 API")
public class AccountController {

    private final AccountRegisterService accountRegisterService;

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    @Operation(summary = "회원 가입")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return accountRegisterService.createAccount(request);
    }
}
