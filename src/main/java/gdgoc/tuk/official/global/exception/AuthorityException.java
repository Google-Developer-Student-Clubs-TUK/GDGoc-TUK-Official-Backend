package gdgoc.tuk.official.global.exception;

import gdgoc.tuk.official.global.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorityException extends IllegalStateException {
    private final ErrorCode errorCode;
}
