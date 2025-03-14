package gdgoc.tuk.official.global.advice;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.AuthorityException;
import gdgoc.tuk.official.global.exception.DuplicationException;
import gdgoc.tuk.official.global.exception.GoogleSpreadSheetsException;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

import gdgoc.tuk.official.global.exception.ValidationException;
import gdgoc.tuk.official.global.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse retryFailed(final ResourceNotFoundException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(DuplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse retryFailed(final DuplicationException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GoogleSpreadSheetsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse retryFailed(final GoogleSpreadSheetsException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(AuthorityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse retryFailed(final AuthorityException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse retryFailed(final ValidationException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    private void loggingError(final ErrorCode errorCode) {
        log.error("ErrorCode : {} , Message : {}", errorCode.getCode(), errorCode.getMsg());
    }
}
