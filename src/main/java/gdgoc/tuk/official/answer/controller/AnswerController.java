package gdgoc.tuk.official.answer.controller;

import gdgoc.tuk.official.answer.dto.AnswerRequestList;
import gdgoc.tuk.official.answer.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/answers")
@Tag(name = "지원자 응답 API")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    @Operation(summary = "지원하기",description = """
        필수응답 정보를 리스트 형식에도 넘겨주셔야하지만, 따로 객체를 만들어 중복으로 넘겨주셔야합니다.
        응답의 순서는 질문의 순서 그대로 넘겨주시면 됩니다.""")
    public void apply(@RequestBody final AnswerRequestList request){
        answerService.apply(request);
    }
}
