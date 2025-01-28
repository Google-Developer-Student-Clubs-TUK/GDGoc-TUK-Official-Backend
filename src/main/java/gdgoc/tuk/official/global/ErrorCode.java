package gdgoc.tuk.official.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * Question Error
     */
    QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST,"100","등록된 질문을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}
