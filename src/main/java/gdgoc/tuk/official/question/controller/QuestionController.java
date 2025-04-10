package gdgoc.tuk.official.question.controller;

import gdgoc.tuk.official.question.dto.QuestionDeleteRequest;
import gdgoc.tuk.official.question.dto.QuestionPageResponse;
import gdgoc.tuk.official.question.dto.QuestionUpdateRequest;
import gdgoc.tuk.official.question.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
@Tag(name = "질문지 API")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    @Operation(summary = "전체 질문 조회", description = "질문 식별자, 질문 내용, 질문 순서를 반환합니다.")
    @PreAuthorize("permitAll()")
    public QuestionPageResponse findAllQuestions() {
        return questionService.findAllQuestionsAndSubQuestionsWithOrder();
    }

    @PostMapping
//    @PreAuthorize("hasRole('LEADER')")
    @Operation(
            summary = "질문 등록/수정/순서변경",
            description =
                    """
          질문을 수정/등록 하는 API입니다.
          새로운 부모 질문(Question)을 등록하는 경우엔 부모 질문의 임시 식별자를 -1, -2, -3으로 증가하도록 요청해야합니다.
          부모 질문(Question)에 딸려있는 자식 질문(SubQuestion)은 생성 시 식별자나 임시식별자는 필요하지 않습니다.
          자식 질문(SubQuestion) 수정 시엔 반환된 식별자와 같이 요청해야합니다.
          질문 순서 목록은 어떠한 요청이더라도 항상 포함시켜 요청해야합니다.
          """)
    public void saveAndModifyQuestions(@RequestBody QuestionUpdateRequest request) {
        questionService.saveAndModifyQuestions(request);
    }

    @DeleteMapping("/parent/{questionId}")
//    @PreAuthorize("hasRole('LEADER')")
    @Operation(summary = "부모 질문 삭제", description = "부모 질문(Question) 식별자를 이용해 질문을 삭제합니다.")
    public void deleteQuestion(
            @PathVariable final Long questionId, @RequestBody QuestionDeleteRequest request) {
        questionService.deleteQuestion(questionId, request);
    }

    @DeleteMapping("/parent/{questionId}/child/{subQuestionId}")
    @Operation(summary = "자식 질문 삭제", description = "자식 질문(SubQuestion) 식별자를 이용해 질문을 삭제합니다.")
    public void deleteSubQuestion(@PathVariable final Long questionId,
        @PathVariable final Long subQuestionId) {
        questionService.deleteSubQuestion(questionId,subQuestionId);
    }
}
