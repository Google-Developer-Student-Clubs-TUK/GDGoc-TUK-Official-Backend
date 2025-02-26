package gdgoc.tuk.official.question.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ValidationException;

public class DeleteNotAllowedException extends ValidationException {

    public DeleteNotAllowedException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
