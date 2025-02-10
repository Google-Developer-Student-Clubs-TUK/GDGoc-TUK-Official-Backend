package gdgoc.tuk.official.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

  /** Question Error 100 ~ 199 */
  QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "100", "등록된 질문을 찾을 수 없습니다."),

  /** Recruitment Error Code 200 ~ 299 */
  GENERATION_DUPLICATED(HttpStatus.BAD_REQUEST, "200", "이미 존재하는 기수입니다."),
  RECRUITMENT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "201", "이미 모집중인 모집이 있습니다."),
  RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "202", "모집 중인 공고가 없습니다."),

  /** Google Spread Sheets Error Code 300 ~ 399 */
  SHEETS_CREATION_FAILED(HttpStatus.BAD_GATEWAY, "300", "구글 스프레드시트 생성에 실패했습니다. 잠시 후 다시 시도해주세요."),

  /** Member Error Code 400 ~ 499 */
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "400", "회원을 찾을 수 없습니다."),

  /**
   *  Applicant Error Code 500 ~ 599
   */
  NOT_ACCEPTED_APPLICANT(HttpStatus.BAD_REQUEST,"500","합격 대상자가 아닙니다."),
  APPLICANT_NOT_FOUND(HttpStatus.NOT_FOUND,"501","지원자를 찾을 수 없습니다."),
  NOT_VALID_EMAIL(HttpStatus.BAD_REQUEST,"502","한국공학대학교 이메일 형식이 아닙니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String msg;
}
