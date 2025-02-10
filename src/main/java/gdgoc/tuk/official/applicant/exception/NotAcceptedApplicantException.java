package gdgoc.tuk.official.applicant.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.AuthorityException;

public class NotAcceptedApplicantException extends AuthorityException {

    public NotAcceptedApplicantException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
