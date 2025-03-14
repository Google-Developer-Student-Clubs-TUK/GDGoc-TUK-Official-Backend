package gdgoc.tuk.official.generationmember.domain;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.global.BaseTimeEntity;
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
public class GenerationMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Accounts accounts;

    private String name;
    private String studentNumber;
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;
    @Enumerated(EnumType.STRING)
    private UniversityYear universityYear;
    @Enumerated(EnumType.STRING)
    private Field field;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String major;
    private String generation;

    @Builder
    public GenerationMember(
            Accounts accounts, String name, String studentNumber,
            final Field field,
            final Gender gender,
            final EnrollmentStatus enrollmentStatus,
            final UniversityYear universityYear, String major,
            final String generation) {
        this.accounts = accounts;
        this.name = name;
        this.studentNumber = studentNumber;
        this.field = field;
        this.gender = gender;
        this.enrollmentStatus = enrollmentStatus;
        this.universityYear = universityYear;
        this.major = major;
        this.generation = generation;
    }
}
