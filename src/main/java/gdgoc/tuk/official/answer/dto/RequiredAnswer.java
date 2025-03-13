package gdgoc.tuk.official.answer.dto;

import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.Gender;
import gdgoc.tuk.official.generationmember.domain.UniversityYear;

public record RequiredAnswer(
        String name,
        String studentNumber,
        EnrollmentStatus enrollmentStatus,
        UniversityYear universityYear,
        Field field,
        Gender gender,
        String email,
        String major) {}
