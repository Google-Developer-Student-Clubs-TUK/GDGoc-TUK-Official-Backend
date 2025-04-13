package gdgoc.tuk.official.question.domain;

import gdgoc.tuk.official.global.BaseTimeEntity;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.question.exception.QuestionNotFoundException;
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
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseTimeEntity {

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SubQuestion> subQuestions = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    private int page;
    private boolean isRequired;
    private boolean isDeletable;

    @Builder
    public Question(
            final String content,
            final QuestionType questionType, int page,
            final boolean isRequired,
            boolean isDeletable) {
        this.content = content;
        this.questionType = questionType;
        this.page = page;
        this.isRequired = isRequired;
        this.isDeletable = isDeletable;
    }

    @Builder
    public Question(
            final String content,
            final QuestionType questionType,
            final boolean isRequired,
            boolean isDeletable,
            List<SubQuestion> subQuestionList, int page) {
        this.content = content;
        this.questionType = questionType;
        this.isRequired = isRequired;
        this.isDeletable = isDeletable;
        this.page = page;
        subQuestionList.forEach(this::addSubQuestion);
    }

    public boolean isNotDeletable() {
        return !this.isDeletable;
    }

    private void modifySubContent(
            final Map<Long, String> modifiedSubQuestionMap, final SubQuestion sq) {
        String modifiedSubContent = modifiedSubQuestionMap.get(sq.getId());
        if (Objects.nonNull(modifiedSubContent)) {
            sq.modifySubContent(modifiedSubContent);
        }
    }

    public void modifyQuestion(
            final String content,
            final QuestionType questionType,
            final boolean isRequired,
            final Map<Long, String> modifiedSubQuestionMap,
            final List<String> newSubQuestions) {
        this.content = content;
        modifyQuestionType(questionType);
        this.isRequired = isRequired;
        if (!modifiedSubQuestionMap.isEmpty()) {
            subQuestions.forEach(sq -> modifySubContent(modifiedSubQuestionMap, sq));
        }
        newSubQuestions.forEach(s -> addSubQuestion(new SubQuestion(s)));
    }

    private void modifyQuestionType(final QuestionType questionType) {
        if (questionType.equals(QuestionType.LONG_TEXT)
                || questionType.equals(QuestionType.SHORT_TEXT)) {
            this.subQuestions.clear();
        }
    }

    public void addSubQuestion(final SubQuestion subQuestion) {
        subQuestion.addParentQuestion(this);
        subQuestions.add(subQuestion);
    }

    public void deleteBySubQuestionId(final Long subQuestionId) {
        SubQuestion subQuestion =
                subQuestions.stream()
                        .filter(sq -> sq.getId().equals(subQuestionId))
                        .findFirst()
                        .orElseThrow(
                                () -> new QuestionNotFoundException(ErrorCode.QUESTION_NOT_FOUND));
        subQuestions.remove(subQuestion);
    }
}
