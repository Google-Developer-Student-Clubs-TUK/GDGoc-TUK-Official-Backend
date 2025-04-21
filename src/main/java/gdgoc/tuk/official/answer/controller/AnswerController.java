package gdgoc.tuk.official.answer.controller;

import gdgoc.tuk.official.answer.dto.AnswerListRequest;
import gdgoc.tuk.official.answer.dto.AnswerResponse;
import gdgoc.tuk.official.answer.service.AnswerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/answers")
@Tag(name = "지원자 응답 API")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    @Operation(
            summary = "지원하기",
            description =
                    """
        회원 신상 정보는 별도 객체를 만들어 넘겨주셔야합니다.
        응답의 순서는 질문의 순서 그대로 넘겨주시면 됩니다.""")
    public void apply(@Valid @RequestBody final AnswerListRequest request) {
        answerService.apply(request);
    }

    @GetMapping("/applicants/{applicantId}")
    //    @PreAuthorize("hasRole('LEADER')")
    @Operation(summary = "지원자 질문&응답 조회")
    public AnswerResponse findApplicantAnswer(@PathVariable final Long applicantId) {
        return answerService.findQuestionAndAnswer(applicantId);
    }
}
