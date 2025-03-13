package gdgoc.tuk.official.questionorder.service;

import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import gdgoc.tuk.official.questionorder.repository.QuestionOrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOrderService {

    private final QuestionOrderRepository questionOrderRepository;

    public List<QuestionOrders> findAllQuestionOrders() {
        return questionOrderRepository.findAll();
    }
}
