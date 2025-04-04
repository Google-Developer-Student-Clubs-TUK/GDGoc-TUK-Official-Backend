package gdgoc.tuk.official.answer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AnswerListRequest(
        @NotNull(message = "회원 정보는 비어있을 수 없습니다.") MemberProfile memberProfile,
        List<AnswerRequest> answers,
        @NotEmpty(message = "질문과 답변 JSON은 필수입니다.") String questionAndAnswerJson) {}
