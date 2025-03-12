package gdgoc.tuk.official.global.security;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.member.domain.Account;
import gdgoc.tuk.official.member.exception.MemberNotFoundException;
import gdgoc.tuk.official.member.repository.AccountRepository;
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
        final Account account = accountRepository.findByEmail(username)
            .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        return new GdgMember(account);
    }
}
