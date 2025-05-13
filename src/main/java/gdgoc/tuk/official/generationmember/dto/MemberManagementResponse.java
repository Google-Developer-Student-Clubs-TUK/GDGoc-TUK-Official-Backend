package gdgoc.tuk.official.generationmember.dto;

import gdgoc.tuk.official.account.domain.Role;
import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.Gender;
import gdgoc.tuk.official.generationmember.domain.UniversityYear;

public record MemberManagementResponse(
        String name,
        Field field,
        String studentNumber,
        String major,
        String email,
        Gender gender,
        UniversityYear universityYear,
        String generation,
        Role role,
        EnrollmentStatus enrollmentStatus) {}
