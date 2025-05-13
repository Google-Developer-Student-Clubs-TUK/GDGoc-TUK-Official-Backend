package gdgoc.tuk.official.answer.dto;

import gdgoc.tuk.official.account.domain.Role;
import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.Gender;
import gdgoc.tuk.official.generationmember.domain.UniversityYear;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MemberProfile(
        @NotBlank(message = "이름은 필수입니다.") String name,
        @NotBlank(message = "학번은 필수입니다.")
                @Pattern(regexp = "\\d{10}", message = "학번은 10자리 숫자여야 합니다.")
                String studentNumber,
        @NotNull(message = "등록 상태는 필수입니다.") EnrollmentStatus enrollmentStatus,
        @NotNull(message = "학년 정보는 필수입니다.") UniversityYear universityYear,
        @NotNull(message = "연락처는 필수입니다.")
                @Pattern(regexp = "\\d{11}", message = "연락처는 '-'제외 11자리여야 합니다.")
                String phoneNumber,
        @NotNull(message = "전공 분야는 필수입니다.") Field field,
        @NotNull(message = "성별은 필수입니다.") Gender gender,
        @Pattern(
                        regexp = "^[A-Za-z0-9._%+-]+@tukorea\\.ac\\.kr$",
                        message = "이메일은 @tukorea.ac.kr 도메인만 허용됩니다.")
                @NotBlank(message = "이메일은 필수 입력값입니다.")
                String email,
        @NotBlank(message = "전공은 필수 입력값입니다.") String major,
        @NotNull(message = "활동할 직책은 필수 입력값입니다.") Role role) {}
