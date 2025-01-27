package gdgoc.tuk.official.question.service;

import gdgoc.tuk.official.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void addQuestion(){
    }
}
