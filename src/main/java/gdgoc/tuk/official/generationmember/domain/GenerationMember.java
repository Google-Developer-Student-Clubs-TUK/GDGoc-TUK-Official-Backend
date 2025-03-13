package gdgoc.tuk.official.generationmember.domain;

import gdgoc.tuk.official.account.domain.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenerationMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Enumerated(EnumType.STRING)
    private Field field;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

    @Enumerated(EnumType.STRING)
    private UniversityYear universityYear;

    private String generation;

    @Builder
    public GenerationMember(
            Account account,
            final Field field,
            final Gender gender,
            final EnrollmentStatus enrollmentStatus,
            final UniversityYear universityYear,
            final String generation) {
        this.account = account;
        this.field = field;
        this.gender = gender;
        this.enrollmentStatus = enrollmentStatus;
        this.universityYear = universityYear;
        this.generation = generation;
    }
}
