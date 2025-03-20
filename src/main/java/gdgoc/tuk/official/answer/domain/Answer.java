package gdgoc.tuk.official.answer.domain;

import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String questionAndAnswer;

    @OneToOne(fetch = FetchType.LAZY)
    private Applicant applicant;

    public Answer(final String questionAndAnswer, final Applicant applicant){
        this.questionAndAnswer = questionAndAnswer;
        this.applicant = applicant;
    }
}
