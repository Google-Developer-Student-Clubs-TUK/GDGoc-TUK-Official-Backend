package gdgoc.tuk.official.generationmember.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

public class GenerationMemberNotFoundException extends ResourceNotFoundException {

    public GenerationMemberNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
