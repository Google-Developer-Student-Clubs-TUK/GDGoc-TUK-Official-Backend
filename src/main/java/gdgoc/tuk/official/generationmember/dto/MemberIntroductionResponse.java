package gdgoc.tuk.official.generationmember.dto;

import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.Gender;

public record MemberIntroductionResponse(
    String name,
    Field field,
    Gender gender
) {}
