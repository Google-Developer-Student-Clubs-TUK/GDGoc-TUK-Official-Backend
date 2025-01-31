package gdgoc.tuk.official.recruitment.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.DuplicationException;

public class RecruitmentDuplicationException extends DuplicationException {

    public RecruitmentDuplicationException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
