package gdgoc.tuk.official.recruitment.dto;

import java.time.LocalDateTime;

public record RecruitmentOpenRequest(
    LocalDateTime openAt, LocalDateTime closeAt, String generation) {}
