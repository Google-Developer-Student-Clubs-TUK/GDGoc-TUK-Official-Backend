package gdgoc.tuk.official.account.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.DuplicationException;

public class DuplicatedRegisterException extends DuplicationException {

    public DuplicatedRegisterException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
