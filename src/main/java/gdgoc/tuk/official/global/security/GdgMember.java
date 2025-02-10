package gdgoc.tuk.official.global.security;

import gdgoc.tuk.official.member.domain.Member;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class GdgMember extends User {
    private Member member;

    public GdgMember(final Member member) {
        super(member.getEmail(),member.getPassword(),
            AuthorityUtils.createAuthorityList(member.getRole().toString()));
        this.member = member;
    }
}
