package gdgoc.tuk.official.generationmember.domain;

import lombok.Getter;

@Getter
public enum UniversityYear {
    FRESHMAN("1학년"),
    SOPHOMORE("2학년"),
    JUNIOR("3학년"),
    SENIOR("4학년");

    private final String content;

    UniversityYear(final String content) {
        this.content = content;
    }
}
