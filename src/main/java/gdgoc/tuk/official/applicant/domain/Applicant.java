package gdgoc.tuk.official.applicant.domain;

import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.Gender;
import gdgoc.tuk.official.generationmember.domain.UniversityYear;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String email;
    private String major;
    private String generation;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @Builder
    public Applicant(
            final String name,
            final String studentNumber,
            final EnrollmentStatus enrollmentStatus,
            final UniversityYear universityYear,
            final Field field,
            final Gender gender,
            final String email,
            final String major,
            String generation) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.enrollmentStatus = enrollmentStatus;
        this.universityYear = universityYear;
        this.field = field;
        this.gender = gender;
        this.email = email;
        this.major = major;
        this.generation = generation;
        this.applicationStatus = ApplicationStatus.PENDING;
    }

    public void approve() {
        this.applicationStatus = ApplicationStatus.ACCEPTED;
    }

    public void reject() {
        this.applicationStatus = ApplicationStatus.REJECTED;
    }
}
