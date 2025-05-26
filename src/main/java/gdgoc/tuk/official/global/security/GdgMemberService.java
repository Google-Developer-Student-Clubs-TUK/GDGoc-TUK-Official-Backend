package gdgoc.tuk.official.global.security;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.exception.AccountNotFoundException;
import gdgoc.tuk.official.account.repository.AccountRepository;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.exception.GenerationMemberNotFoundException;
import gdgoc.tuk.official.generationmember.repository.GenerationMemberRepository;
import gdgoc.tuk.official.global.ErrorCode;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GdgMemberService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final GenerationMemberRepository generationMemberRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Accounts accounts =
                accountRepository
                        .findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다."));
        GenerationMember generationMember =
                generationMemberRepository
                        .findTopByAccountsOrderByCreatedAt(accounts)
                        .orElseThrow(
                                () ->
                                        new GenerationMemberNotFoundException(
                                                ErrorCode.GENERATION_MEMBER_NOT_FOUND));
        return new GdgMember(accounts, generationMember);
    }
}
