package gdgoc.tuk.official.global.generator;

import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.domain.QuestionType;
import gdgoc.tuk.official.question.domain.SubQuestion;
import gdgoc.tuk.official.question.repository.QuestionRepository;
import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import gdgoc.tuk.official.questionorder.repository.QuestionOrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberProfileQuestionGenerator implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final QuestionOrderRepository questionOrderRepository;

    @Override
    public void run(final String... args) throws Exception {
        List<Question> questions =
                List.of(
                        new Question("이름", QuestionType.SHORT_TEXT,0, true, false),
                        new Question("연락처", QuestionType.SHORT_TEXT,0, true, false),
                        new Question("학교 이메일", QuestionType.EMAIL,0, true, false),
                        new Question(
                                "학년",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(
                                        new SubQuestion("1학년"),
                                        new SubQuestion("2학년"),
                                        new SubQuestion("3학년"),
                                        new SubQuestion("4학년")),0),
                        new Question(
                                "직군",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(
                                        new SubQuestion("프론트엔드"),
                                        new SubQuestion("백엔드"),
                                        new SubQuestion("디자인")),0),
                        new Question(
                                "오거나이저/멤버",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(new SubQuestion("오거나이저"), new SubQuestion("멤버")),0),
                        new Question("학번", QuestionType.SHORT_TEXT,0, true, false),
                        new Question(
                                "학과",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(
                                        new SubQuestion("컴퓨터공학과"),
                                        new SubQuestion("미디어디자인공학과"),
                                        new SubQuestion("소프트웨어학과"),
                                        new SubQuestion("IT경영학과")),0),
                        new Question(
                                "성별",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(new SubQuestion("남"), new SubQuestion("여")),0),
                        new Question(
                                "학적 상태",
                                QuestionType.SINGLE_CHOICE,
                                true,
                                false,
                                List.of(new SubQuestion("재학"), new SubQuestion("휴학")),0));
        List<Question> savedQuestions = questionRepository.saveAll(questions);
        List<QuestionOrders> questionOrders = new ArrayList<>();
        for (int i = 0; i < savedQuestions.size(); i++) {
            questionOrders.add(new QuestionOrders(savedQuestions.get(i).getId(), i + 1));
        }
        questionOrderRepository.saveAll(questionOrders);
    }
}
