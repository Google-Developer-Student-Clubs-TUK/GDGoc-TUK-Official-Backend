package gdgoc.tuk.official.applicant.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

public class ApplicantNotFoundException extends ResourceNotFoundException {

    public ApplicantNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
