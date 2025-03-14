package gdgoc.tuk.official.generationmember.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.DuplicationException;

public class DuplicatedGenerationMemberException extends DuplicationException {

    public DuplicatedGenerationMemberException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
