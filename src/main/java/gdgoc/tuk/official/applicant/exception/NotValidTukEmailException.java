package gdgoc.tuk.official.applicant.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ValidationException;

public class NotValidTukEmailException extends ValidationException {

    public NotValidTukEmailException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
