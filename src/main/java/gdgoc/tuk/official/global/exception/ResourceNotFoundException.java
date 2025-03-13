package gdgoc.tuk.official.global.exception;

import gdgoc.tuk.official.global.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
