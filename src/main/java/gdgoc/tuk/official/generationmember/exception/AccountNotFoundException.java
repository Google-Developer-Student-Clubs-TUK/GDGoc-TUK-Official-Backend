package gdgoc.tuk.official.generationmember.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

public class AccountNotFoundException extends ResourceNotFoundException {

    public AccountNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
