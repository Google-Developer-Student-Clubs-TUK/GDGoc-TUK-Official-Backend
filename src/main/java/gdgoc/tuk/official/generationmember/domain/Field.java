package gdgoc.tuk.official.generationmember.domain;

import lombok.Getter;

@Getter
public enum Field {
    FRONT_END("프론트엔드"),
    BACK_END("백엔드"),
    DESIGN("디자인");

    private final String content;

    Field(final String content) {
        this.content = content;
    }
}
