package gdgoc.tuk.official.account.domain;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_MEMBER("ROLE_MEMBER"),
    ROLE_ORGANIZER("ROLE_ORGANIZER"),
    ROLE_LEADER("ROLE_LEADER");

    private final String content;

    Role(final String content) {
        this.content = content;
    }
}
