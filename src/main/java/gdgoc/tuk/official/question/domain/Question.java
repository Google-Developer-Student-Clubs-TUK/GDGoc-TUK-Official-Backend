package gdgoc.tuk.official.question.domain;

import gdgoc.tuk.official.global.BaseTimeEntity;

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
    private final List<SubQuestion> subQuestions = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    private boolean isRequired;
    private boolean deletable;

    @Builder
    public Question(
            final String content, final QuestionType questionType, final boolean isRequired,
        boolean deletable) {
        this.content = content;
        this.questionType = questionType;
        this.isRequired = isRequired;
        this.deletable = deletable;
    }

    public boolean isNotDeletable(){
        return !this.deletable;
    }

    private void modifySubContent(final Map<Long, String> modifiedSubQuestionMap,
        final SubQuestion sq) {
        String modifiedSubContent = modifiedSubQuestionMap.get(sq.getId());
        if( Objects.nonNull(modifiedSubContent)) sq.modifySubContent(modifiedSubContent);
    }

    public void modifyContent(
            final String content,
            final QuestionType questionType,
            final boolean isRequired,
            final Map<Long, String> modifiedSubQuestionMap,
        final List<String> newSubQuestions
        ) {
        this.content = content;
        this.questionType = questionType;
        this.isRequired = isRequired;
        if (!modifiedSubQuestionMap.isEmpty()) {
            subQuestions.forEach(sq -> modifySubContent(modifiedSubQuestionMap, sq));
        }
        newSubQuestions.forEach(s->subQuestions.add(new SubQuestion(s)));
    }

    public void addSubQuestion(final SubQuestion subQuestion) {
        subQuestion.addParentQuestion(this);
        subQuestions.add(subQuestion);
    }
}
