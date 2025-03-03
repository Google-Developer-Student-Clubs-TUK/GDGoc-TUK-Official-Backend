package gdgoc.tuk.official.questionorder.domain;

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
public class QuestionOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questionId;
    private Long orders;

    public void changeOrder(final Long order){
        this.orders = order;
    }

    public QuestionOrders(final Long questionId, final Long orders) {
        this.questionId = questionId;
        this.orders = orders;
    }
}
