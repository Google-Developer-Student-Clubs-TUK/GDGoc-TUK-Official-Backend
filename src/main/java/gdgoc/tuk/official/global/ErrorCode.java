package gdgoc.tuk.official.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    /** Global Error 0~99 */
    AUTHORITY_NOT_VALID(HttpStatus.FORBIDDEN, "000", "접근 권한이 없습니다."),

    /** Question Error 100 ~ 199 */
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "100", "등록된 질문을 찾을 수 없습니다."),
    DELETE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "101", "삭제할 수 없는 질문입니다."),
    MODIFY_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "102", "모집이 진행 중인 경우 질문을 수정할 수 없습니다."),

    /** Recruitment Error Code 200 ~ 299 */
    GENERATION_DUPLICATED(HttpStatus.BAD_REQUEST, "200", "이미 존재하는 기수입니다."),
    RECRUITMENT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "201", "이미 모집중인 모집이 있습니다."),
    ON_GOING_RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "202", "모집 중인 공고가 없습니다."),
    INVALID_GENERATION(HttpStatus.BAD_REQUEST, "203", "모집 기수는 올해 혹은 내년으로 설정 가능합니다."),

    /** Google Spread Sheets Error Code 300 ~ 399 */
    SHEETS_CREATION_FAILED(HttpStatus.BAD_GATEWAY, "300", "구글 스프레드시트 생성에 실패했습니다. 잠시 후 다시 시도해주세요."),

    /** Accounts Error Code 400 ~ 499 */
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "400", "계정을 찾을 수 없습니다."),
    DUPLICATED_ACCOUNT(HttpStatus.BAD_REQUEST, "401", "중복된 계정 가입입니다."),

    /** Applicant Error Code 500 ~ 599 */
    NOT_ACCEPTED_APPLICANT(HttpStatus.BAD_REQUEST, "500", "합격 대상자가 아닙니다."),
    APPLICANT_NOT_FOUND(HttpStatus.NOT_FOUND, "501", "지원자를 찾을 수 없습니다."),
    NOT_VALID_EMAIL(HttpStatus.BAD_REQUEST, "502", "한국공학대학교 이메일 형식이 아닙니다."),

    /** Email Error Code 600 ~ 699 */
    NOT_VERIFIED_EMAIL(HttpStatus.NOT_FOUND, "600", "인증되지 않은 메일입니다. 인증을 시도해주세요."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "601", "잘못된 인증코드입니다."),

    /** Generation Member Error Code 700 ~ 799 */
    DUPLICATED_GENERATION_MEMBER(HttpStatus.NOT_FOUND, "700", "해당 계정으로 현재 기수에 존재하는 회원입니다."),
    GENERATION_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "701", "등록된 기수 정보가 없습니다."),

    /** Answer Error Code 800 ~ 899 */
    DUPLICATED_ANSWER(HttpStatus.BAD_REQUEST, "800", "중복된 지원서입니다."),
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "801", "지원자의 응답을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}
