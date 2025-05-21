package gdgoc.tuk.official.recruitment.domain;


import gdgoc.tuk.official.recruitment.exception.InvalidGenerationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class RecruitmentTest {

    @Test
    @DisplayName("현재 연도를 기준으로 모집을 생성할 경우 Recruitment가 생성된다.")
    void createCurrentYearRecruitment() {
        // given
        String currentYear = String.valueOf(LocalDateTime.now().getYear());
        String spreadSheetsId = "fakeSpreadSheetsId";
        LocalDateTime openAt = LocalDateTime.now();
        LocalDateTime closeAt = openAt.plusDays(10);
        // when & then
        Assertions.assertThatCode(()->new Recruitment(currentYear,spreadSheetsId,openAt,closeAt)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("현재 연도+1를 기준으로 모집을 생성할 경우 Recruitment가 생성된다.")
    void createNextYearRecruitment() {
        // given
        String currentYear = String.valueOf(LocalDateTime.now().plusYears(1L).getYear());
        String spreadSheetsId = "fakeSpreadSheetsId";
        LocalDateTime openAt = LocalDateTime.now();
        LocalDateTime closeAt = openAt.plusDays(10);
        // when & then
        Assertions.assertThatCode(()->new Recruitment(currentYear,spreadSheetsId,openAt,closeAt)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("현재 연도-1를 기준으로 모집을 생성할 경우 예외가 발생한다.")
    void minusOneYearRecruitment() {
        // given
        String currentYear = String.valueOf(LocalDateTime.now().minusYears(1L).getYear());
        String spreadSheetsId = "fakeSpreadSheetsId";
        LocalDateTime openAt = LocalDateTime.now();
        LocalDateTime closeAt = openAt.plusDays(10);
        // when & then
        Assertions.assertThatCode(()->new Recruitment(currentYear,spreadSheetsId,openAt,closeAt)).isInstanceOf(
            InvalidGenerationException.class);
    }

    @Test
    @DisplayName("현재 연도+2를 기준으로 모집을 생성할 경우 예외가 발생한다.")
    void plushTwoYearRecruitment() {
        // given
        String currentYear = String.valueOf(LocalDateTime.now().plusYears(2L).getYear());
        String spreadSheetsId = "fakeSpreadSheetsId";
        LocalDateTime openAt = LocalDateTime.now();
        LocalDateTime closeAt = openAt.plusDays(10);
        // when & then
        Assertions.assertThatCode(()->new Recruitment(currentYear,spreadSheetsId,openAt,closeAt)).isInstanceOf(
            InvalidGenerationException.class);
    }
}
