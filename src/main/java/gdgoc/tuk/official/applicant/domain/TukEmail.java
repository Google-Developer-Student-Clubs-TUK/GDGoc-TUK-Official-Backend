package gdgoc.tuk.official.applicant.domain;

import gdgoc.tuk.official.applicant.exception.NotValidTukEmailException;
import gdgoc.tuk.official.global.ErrorCode;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TukEmail {
    private static final Pattern TUK_EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9._%+-]+@tukorea\\.ac\\.kr$");
    private String email;

    public TukEmail(final String email) {
        checkValidTukEmail(email);
        this.email = email;
    }

    private void checkValidTukEmail(final String email){
        if(!TUK_EMAIL_PATTERN.matcher(email).matches()){
            throw new NotValidTukEmailException(ErrorCode.NOT_VALID_EMAIL);
        }
    }
}
