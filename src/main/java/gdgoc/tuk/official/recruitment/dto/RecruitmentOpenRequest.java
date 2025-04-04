package gdgoc.tuk.official.recruitment.dto;

import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public record RecruitmentOpenRequest(
        LocalDateTime openAt, LocalDateTime closeAt,
        @Pattern(regexp = "\\d{4}", message = "기수는 4자리 연도여야합니다.")String generation) {}
