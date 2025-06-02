package gdgoc.tuk.official.recruitment.service;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import gdgoc.tuk.official.answer.repository.SpreadSheetsPrimaryKeyRepository;
import gdgoc.tuk.official.config.TestConfig;
import gdgoc.tuk.official.google.initializer.SpreadSheetsInitializer;
import gdgoc.tuk.official.google.service.SpreadSheetsQuestionService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.dto.GenerationResponse;
import gdgoc.tuk.official.recruitment.dto.RecruitmentOpenRequest;
import gdgoc.tuk.official.recruitment.exception.RecruitmentDuplicationException;
import gdgoc.tuk.official.recruitment.exception.RecruitmentNotExistException;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Import(TestConfig.class)
class RecruitmentServiceTest {

    @Autowired
    private RecruitmentRepository recruitmentRepository;
    @MockitoBean
    private SpreadSheetsInitializer spreadSheetsInitializer;
    @MockitoBean
    private SpreadSheetsPrimaryKeyRepository spreadSheetsPrimaryKeyRepository;
    @MockitoBean
    private SpreadSheetsQuestionService spreadSheetsQuestionService;
    @Autowired
    private RecruitmentService recruitmentService;


    @Test
    @DisplayName("진행 중인 모집이 있을 경우 예외를 발생시킨다.")
    void throwIfRecruitmentExist() {
        // given
        LocalDateTime now = LocalDateTime.now();
        RecruitmentOpenRequest firstRequest = new RecruitmentOpenRequest(
            now,now.plusDays(5L),"2025"
        );
        RecruitmentOpenRequest secondRequest = new RecruitmentOpenRequest(
            now,now.plusDays(5L),"2025"
        );
        recruitmentService.openRecruitment(firstRequest);
        // when & then
        assertThatThrownBy(() -> recruitmentService.openRecruitment(secondRequest))
            .isInstanceOf(RecruitmentDuplicationException.class);
    }

    @Test
    @DisplayName("진행 중인 모집이 없을 경우 모집이 생성된다.")
    void createRecruitment() {
        // given
        LocalDateTime now = LocalDateTime.now();
        RecruitmentOpenRequest firstRequest = new RecruitmentOpenRequest(
            now.minusDays(10L),now.minusDays(5L),"2025"
        );
        RecruitmentOpenRequest secondRequest = new RecruitmentOpenRequest(
            now,now.plusDays(5L),"2025"
        );
        recruitmentService.openRecruitment(firstRequest);
        // when
        recruitmentService.openRecruitment(secondRequest);
        // then
        boolean isOpen = recruitmentService.getRecruitmentStatus().isOpen();
        assertThat(isOpen).isTrue();
    }

    @Test
    @DisplayName("진행 중인 모집이 없을 경우 모집 상태는 거짓을 반환한다.")
    void getFalseRecruitmentStatus() {
        // given
        // when
        boolean isOpen = recruitmentService.getRecruitmentStatus().isOpen();
        // then
        assertThat(isOpen).isFalse();
    }

    @Test
    @DisplayName("진행 중인 모집이 있을 경우 모집 상태는 참을 반환한다.")
    void getTrueRecruitmentStatus() {
        // given
        LocalDateTime now = LocalDateTime.now();
        RecruitmentOpenRequest firstRequest = new RecruitmentOpenRequest(
            now,now.plusDays(5L),"2025"
        );
        recruitmentService.openRecruitment(firstRequest);
        // when
        boolean isOpen = recruitmentService.getRecruitmentStatus().isOpen();
        // then
        assertThat(isOpen).isTrue();
    }

    @Test
    @DisplayName("같은 연도에 두 번의 모집이 존재해도 기수는 1개만 반환된다.")
    void getAllDistinctGenerations() {
        // given
        int expectedGenerationSize = 1;
        LocalDateTime now = LocalDateTime.now();
        RecruitmentOpenRequest firstRequest = new RecruitmentOpenRequest(
            now.minusDays(10L),now.minusDays(5L),"2025"
        );
        RecruitmentOpenRequest secondRequest = new RecruitmentOpenRequest(
            now.minusDays(3L),now.minusDays(1L),"2025"
        );
        recruitmentService.openRecruitment(firstRequest);
        recruitmentService.openRecruitment(secondRequest);
        // when
        GenerationResponse response = recruitmentService.getGenerations();
        // then
        assertThat(response.generations()).hasSize(expectedGenerationSize);
    }

    @Test
    @DisplayName("모집이 진행중이면 기수정보 반환에 포함시키지 않는다.")
    void notContainGenerationOnGoingRecruitment() {
        // given
        int expectedGenerationSize = 0;
        LocalDateTime now = LocalDateTime.now();
        RecruitmentOpenRequest firstRequest = new RecruitmentOpenRequest(
            now,now.plusDays(5L),"2025"
        );
        recruitmentService.openRecruitment(firstRequest);
        // when
        GenerationResponse response = recruitmentService.getGenerations();
        // then
        assertThat(response.generations()).hasSize(expectedGenerationSize);
    }

    @Test
    @DisplayName("진행 중인 모집이 없으면 예외를 발생시킨다")
    void throwIfOnGoingRecruitmentNotExist() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Recruitment closedRecruitment = new Recruitment("2025","url", "fakeId", now.minusDays(10L), now.minusDays(5L));
        recruitmentRepository.save(closedRecruitment);
        // when & then
        assertThatThrownBy(() -> recruitmentService.getOnGoingRecruitment(now))
            .isInstanceOf(RecruitmentNotExistException.class);
    }

    @Test
    @DisplayName("진행 중인 모집이 존재하면 Recuritment를 반환한다.")
    void getOnGoingRecruitment() {
        // given
        String generation = "2025";
        LocalDateTime now = LocalDateTime.now();
        Recruitment onGoingRecruitment = new Recruitment(generation,"url", "fakeId", now, now.plusDays(5L));
        recruitmentRepository.save(onGoingRecruitment);
        // when
        Recruitment savedOnGoingRecruitment = recruitmentService.getOnGoingRecruitment(now());
        // then
        assertThat(savedOnGoingRecruitment.getId()).isEqualTo(onGoingRecruitment.getId());
    }
}
