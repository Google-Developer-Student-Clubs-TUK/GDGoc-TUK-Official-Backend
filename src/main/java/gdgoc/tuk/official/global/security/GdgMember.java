package gdgoc.tuk.official.global.security;

import gdgoc.tuk.official.account.domain.Account;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class GdgMember extends User {
    private final Account account;

    public GdgMember(final Account account) {
        super(
                account.getEmail(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole().toString()));
        this.account = account;
    }
}
