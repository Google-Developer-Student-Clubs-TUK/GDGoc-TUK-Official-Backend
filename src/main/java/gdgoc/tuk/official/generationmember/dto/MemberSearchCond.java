package gdgoc.tuk.official.generationmember.dto;

import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;

public record MemberSearchCond(
        Field field, String generation, EnrollmentStatus enrollmentStatus, String name) {}
