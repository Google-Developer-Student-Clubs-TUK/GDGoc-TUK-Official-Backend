package gdgoc.tuk.official.questionorder.domain;

import gdgoc.tuk.official.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionOrders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questionId;
    private Integer orders;

    public QuestionOrders(final Long questionId, final Integer orders) {
        this.questionId = questionId;
        this.orders = orders;
    }

    public void changeOrder(final Integer order) {
        this.orders = order;
    }
}
