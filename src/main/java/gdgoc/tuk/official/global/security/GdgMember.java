package gdgoc.tuk.official.global.security;

import gdgoc.tuk.official.account.domain.Accounts;

import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class GdgMember extends User {
    private final Accounts accounts;
    private final GenerationMember generationMember;

    public GdgMember(final Accounts accounts, final GenerationMember generationMember) {
        super(
                accounts.getEmail(),
                accounts.getPassword(),
                AuthorityUtils.createAuthorityList(accounts.getRole().toString()));
        this.accounts = accounts;
        this.generationMember = generationMember;
    }
}
