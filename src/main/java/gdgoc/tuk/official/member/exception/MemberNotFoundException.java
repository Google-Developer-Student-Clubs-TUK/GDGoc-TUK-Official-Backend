package gdgoc.tuk.official.member.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

public class MemberNotFoundException extends ResourceNotFoundException {

    public MemberNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
