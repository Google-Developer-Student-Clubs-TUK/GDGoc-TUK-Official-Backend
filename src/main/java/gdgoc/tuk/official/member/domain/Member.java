package gdgoc.tuk.official.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

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

    public Member(final Account account, final Field field, final Gender gender,
        final String generation) {
        this.account = account;
        this.field = field;
        this.gender = gender;
        this.generation = generation;
    }
}
