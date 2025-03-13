package gdgoc.tuk.official.global.security;

import gdgoc.tuk.official.account.domain.Account;
import gdgoc.tuk.official.account.repository.AccountRepository;
import gdgoc.tuk.official.generationmember.exception.AccountNotFoundException;
import gdgoc.tuk.official.global.ErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GdgMemberService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Account account =
                accountRepository
                        .findByEmail(username)
                        .orElseThrow(
                                () -> new AccountNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
        return new GdgMember(account);
    }
}
