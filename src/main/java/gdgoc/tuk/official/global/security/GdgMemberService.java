package gdgoc.tuk.official.global.security;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.repository.AccountRepository;
import gdgoc.tuk.official.account.exception.AccountNotFoundException;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.exception.GenerationMemberNotFoundException;
import gdgoc.tuk.official.generationmember.repository.GenerationMemberRepositoryRepository;
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
    private final GenerationMemberRepositoryRepository generationMemberRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // TODO: 필터에서 예외 잡아라
        final Accounts accounts =
                accountRepository
                        .findByEmail(username)
                        .orElseThrow(
                                () -> new AccountNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
        GenerationMember generationMember = generationMemberRepository.findTopByAccountsOrderByCreatedAt(
                accounts)
            .orElseThrow(() -> new GenerationMemberNotFoundException(ErrorCode.QUESTION_NOT_FOUND));
        return new GdgMember(accounts,generationMember);
    }
}
