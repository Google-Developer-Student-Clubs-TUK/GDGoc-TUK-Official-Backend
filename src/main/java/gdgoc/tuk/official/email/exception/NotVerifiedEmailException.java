package gdgoc.tuk.official.email.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ValidationException;

public class NotVerifiedEmailException extends ValidationException {

    public NotVerifiedEmailException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
