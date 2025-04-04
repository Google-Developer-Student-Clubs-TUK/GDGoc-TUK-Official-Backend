package gdgoc.tuk.official.question.temporary;

import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.domain.QuestionType;
import gdgoc.tuk.official.question.domain.SubQuestion;
import gdgoc.tuk.official.question.repository.QuestionRepository;
import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import gdgoc.tuk.official.questionorder.repository.QuestionOrderRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestQuestionGenerator implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final QuestionOrderRepository questionOrderRepository;

    @Override
    public void run(final String... args) throws Exception {
        List<Question> questions =
                List.of(
                        new Question("이름", QuestionType.SHORT_TEXT, true, false),
                        new Question("연락처", QuestionType.SHORT_TEXT, true, false),
                        new Question("학교 이메일", QuestionType.EMAIL, true, false),
                        new Question(
                                "직군",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(
                                        new SubQuestion("프론트엔드"),
                                        new SubQuestion("백엔드"),
                                        new SubQuestion("디자인"))),
                        new Question(
                                "오거나이저/멤버",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(new SubQuestion("오거나이저"), new SubQuestion("멤버"))),
                        new Question("학번", QuestionType.SHORT_TEXT, true, false),
                        new Question(
                                "학과",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(
                                        new SubQuestion("컴퓨터공학과"),
                                        new SubQuestion("미디어디자인공학과"),
                                        new SubQuestion("소프트웨어학과"),
                                        new SubQuestion("IT경영학과"))),
                        new Question(
                                "성별",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(new SubQuestion("남"), new SubQuestion("여"))));
        List<Question> savedQuestions = questionRepository.saveAll(questions);
        List<QuestionOrders> questionOrders = new ArrayList<>();
        for (int i = 0; i < savedQuestions.size(); i++) {
            questionOrders.add(new QuestionOrders(savedQuestions.get(i).getId(), i + 1));
        }
        questionOrderRepository.saveAll(questionOrders);
    }
}
