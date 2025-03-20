package gdgoc.tuk.official.answer.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

public class AnswerNotFoundException extends ResourceNotFoundException {

    public AnswerNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
