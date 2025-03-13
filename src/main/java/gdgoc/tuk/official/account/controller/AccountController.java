package gdgoc.tuk.official.account.controller;

import gdgoc.tuk.official.account.dto.RegisterRequest;
import gdgoc.tuk.official.account.dto.RegisterResponse;
import gdgoc.tuk.official.account.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Tag(name = "계정 API")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return accountService.createAccount(request);
    }
}
