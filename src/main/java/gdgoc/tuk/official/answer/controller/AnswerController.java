package gdgoc.tuk.official.answer.controller;

import gdgoc.tuk.official.answer.dto.ApplyRequestList;
import gdgoc.tuk.official.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public void apply(@RequestBody final ApplyRequestList request){
        answerService.apply(request);
    }
}
