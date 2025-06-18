package gdgoc.tuk.official.global.response;

import gdgoc.tuk.official.global.ErrorCode;

public record ErrorResponse(
        int httpStatusCode,
        String httpStatusMessage,
        String serverErrorCode,
        String serverErrorMessage) {

    public ErrorResponse(final ErrorCode errorCode) {
        this(
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().getReasonPhrase(),
                errorCode.getCode(),
                errorCode.getMsg());
    }

}
