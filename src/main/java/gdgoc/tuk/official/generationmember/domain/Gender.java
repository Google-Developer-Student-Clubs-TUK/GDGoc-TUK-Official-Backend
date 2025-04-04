package gdgoc.tuk.official.generationmember.domain;

import lombok.Getter;

@Getter
public enum Gender {
    WOMAN("여"),
    MAN("남");

    private final String content;

    Gender(final String content) {
        this.content = content;
    }
}
