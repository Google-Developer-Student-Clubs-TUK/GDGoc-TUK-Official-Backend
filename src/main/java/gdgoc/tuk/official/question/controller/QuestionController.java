package gdgoc.tuk.official.question.controller;

import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.dto.QuestionDeleteRequest;
import gdgoc.tuk.official.question.dto.QuestionListResponse;
import gdgoc.tuk.official.question.dto.QuestionUpdateRequest;
import gdgoc.tuk.official.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
  public QuestionListResponse findAllQuestion() {
    return questionService.findAllQuestionResponses();
  }

  @PostMapping
  @Operation(
      summary = "질문 등록",
      description =
          """
          질문을 수정/등록 하는 API입니다.
          새로운 질문을 등록하는 경우엔 질문의 임시 식별자를 -1, -2, -3으로 넣어주시고 새로운 질문이 존재하는 경우엔
          모든 질문의 순서를 같이 넘겨야합니다.
          수정만 하는 경우엔 전체 질문의 순서를 넘기지 않아도 됩니다.
          질문 순서를 변경하는 경우에도 모든 질문의 순서를 넘겨야합니다.""")
  public void updateQuestions(@RequestBody QuestionUpdateRequest request) {
    questionService.updateQuestions(request);
  }

  @DeleteMapping("/{questionId}")
  @Operation(summary = "질문 삭제", description = "질문 식별자를 이용해 질문을 삭제합니다.")
  public void deleteQuestion(@PathVariable final Long questionId, @RequestBody
      QuestionDeleteRequest request) {
    questionService.deleteQuestion(questionId,request);
  }
}
