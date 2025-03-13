package gdgoc.tuk.official.question.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    private boolean isRequired;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<SubQuestion> subQuestions = new ArrayList<>();

    @Builder
    public Question(
            final String content, final QuestionType questionType, final boolean isRequired) {
        this.content = content;
        this.questionType = questionType;
        this.isRequired = isRequired;
    }

    public void modifyContent(
            final String content,
            final QuestionType questionType,
            final boolean isRequired,
            final Map<Long, String> modifiedSubQuestionMap) {
        this.content = content;
        this.questionType = questionType;
        this.isRequired = isRequired;
        if (!modifiedSubQuestionMap.isEmpty()) {
            subQuestions.forEach(
                    sq -> {
                        String modifiedSubContent = modifiedSubQuestionMap.get(sq.getId());
                        sq.modifySubContent(modifiedSubContent);
                    });
        }
    }

    public void addSubQuestion(final SubQuestion subQuestion) {
        subQuestion.addParentQuestion(this);
        subQuestions.add(subQuestion);
    }
}
