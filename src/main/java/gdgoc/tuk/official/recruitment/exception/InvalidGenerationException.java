package gdgoc.tuk.official.recruitment.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ValidationException;

public class InvalidGenerationException extends ValidationException {

    public InvalidGenerationException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
