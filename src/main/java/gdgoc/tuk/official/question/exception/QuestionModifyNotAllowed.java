package gdgoc.tuk.official.question.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ValidationException;

public class QuestionModifyNotAllowed extends ValidationException {

    public QuestionModifyNotAllowed(final ErrorCode errorCode) {
        super(errorCode);
    }
}
