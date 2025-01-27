package gdgoc.tuk.official.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    @GetMapping
    public
    
    @PostMapping
    public void addQuestion(){
        
    }

    @DeleteMapping
    public void deleteQuestion(){

    }

    @PatchMapping
    public void updateQuestion(){

    }

    
}
