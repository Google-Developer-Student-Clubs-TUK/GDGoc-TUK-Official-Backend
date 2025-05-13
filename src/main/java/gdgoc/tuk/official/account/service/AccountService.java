package gdgoc.tuk.official.account.service;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.exception.AccountNotFoundException;
import gdgoc.tuk.official.account.repository.AccountRepository;
import gdgoc.tuk.official.global.ErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public boolean isNotRegistered(final String email) {
        return !accountRepository.existsByEmail(email);
    }

    public Accounts getAccountByEmail(final String email) {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
    }
}
