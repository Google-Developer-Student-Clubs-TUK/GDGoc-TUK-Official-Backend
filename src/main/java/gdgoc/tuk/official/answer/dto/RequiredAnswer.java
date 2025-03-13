package gdgoc.tuk.official.answer.dto;

import gdgoc.tuk.official.member.domain.EnrollmentStatus;
import gdgoc.tuk.official.member.domain.Field;
import gdgoc.tuk.official.member.domain.Gender;
import gdgoc.tuk.official.member.domain.UniversityYear;

public record RequiredAnswer(
    String name,
    String studentNumber,
    EnrollmentStatus enrollmentStatus,
    UniversityYear universityYear,
    Field field,
    Gender gender,
    String email,
    String major
) {}
