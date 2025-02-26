package gdgoc.tuk.official.answer.dto;

import gdgoc.tuk.official.member.domain.Field;
import gdgoc.tuk.official.member.domain.Gender;

public record RequiredAnswer(
    String name,
    String studentNumber,
    Field field,
    Gender gender,
    String email,
    String major
) {}
