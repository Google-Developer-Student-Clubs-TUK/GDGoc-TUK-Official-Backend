package gdgoc.tuk.official.question.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  @OneToMany(fetch = FetchType.LAZY)
  private List<SubQuestion> subQuestions = new ArrayList<>();

  @Builder
  public Question(final String content, final QuestionType questionType, final boolean isRequired) {
    this.content = content;
    this.questionType = questionType;
    this.isRequired = isRequired;
  }

  public void addSubQuestion(final SubQuestion subQuestion){
    subQuestions.add(subQuestion);
  }
}
