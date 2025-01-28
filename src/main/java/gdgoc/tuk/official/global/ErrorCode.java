package gdgoc.tuk.official.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    DUMMY(HttpStatus.BAD_REQUEST,"000","요청 파라미터 검증에 실패했습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}
