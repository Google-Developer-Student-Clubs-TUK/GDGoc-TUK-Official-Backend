package gdgoc.tuk.official.answer.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.DuplicationException;

public class DuplicatedAnswerException extends DuplicationException {

    public DuplicatedAnswerException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
