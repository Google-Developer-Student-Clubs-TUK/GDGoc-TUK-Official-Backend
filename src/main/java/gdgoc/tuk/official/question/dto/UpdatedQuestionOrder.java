package gdgoc.tuk.official.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatedQuestionOrder {
    private Long questionId;
    private Integer order;

    public void updateQuestionId(final Long savedQuestionId){
        this.questionId = savedQuestionId;
    }
}
