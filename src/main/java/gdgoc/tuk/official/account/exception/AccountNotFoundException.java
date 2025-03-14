package gdgoc.tuk.official.account.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

public class AccountNotFoundException extends ResourceNotFoundException {

    public AccountNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
