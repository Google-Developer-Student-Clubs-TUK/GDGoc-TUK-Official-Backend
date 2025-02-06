package gdgoc.tuk.official.recruitment.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

public class RecruitmentNotExistException extends ResourceNotFoundException {

    public RecruitmentNotExistException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
