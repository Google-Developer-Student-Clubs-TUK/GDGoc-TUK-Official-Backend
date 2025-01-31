package gdgoc.tuk.official.answer.service;

import gdgoc.tuk.official.answer.domain.Answer;
import gdgoc.tuk.official.answer.dto.ApplyRequestList;
import gdgoc.tuk.official.answer.repository.AnswerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

  private final AnswerRepository answerRepository;

  public void apply(final ApplyRequestList request){
    final List<Answer> answerList = request.applyRequests().stream()
        .map(a -> new Answer(a.answer(), a.questionId())).toList();
    answerRepository.saveAll(answerList);
  }
}
