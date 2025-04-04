package gdgoc.tuk.official.generationmember.domain;

import lombok.Getter;

@Getter
public enum EnrollmentStatus {
    ENROLLED("재학"),
    LEAVE_OF_ABSENCE("휴학");

    private final String content;

    EnrollmentStatus(final String content) {
        this.content = content;
    }
}
