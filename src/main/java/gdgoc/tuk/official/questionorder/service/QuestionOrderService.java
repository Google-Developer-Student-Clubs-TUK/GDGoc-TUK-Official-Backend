package gdgoc.tuk.official.questionorder.service;

import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import gdgoc.tuk.official.questionorder.repository.QuestionOrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionOrderService {

    private final QuestionOrderRepository questionOrderRepository;

    public List<QuestionOrders> findAllQuestionOrders(){
        return questionOrderRepository.findAll();
    }
}
