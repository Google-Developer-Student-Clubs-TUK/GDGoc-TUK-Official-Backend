package gdgoc.tuk.official.recruitment.service;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gdgoc.tuk.official.config.TestConfig;

import gdgoc.tuk.official.question.exception.QuestionModifyNotAllowed;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(TestConfig.class)
class RecruitmentValidatorTest {

    @Autowired
    private RecruitmentValidator recruitmentValidator;
    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Test
    @DisplayName("모집이 진행 중인 경우 질문지를 수정할 경우 예외가 발생한다.")
    void throwIfModifyQuestionsDuringRecruiting() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Recruitment closedRecruitment = new Recruitment("2025", "fakeId", now, now.plusDays(5L));
        recruitmentRepository.save(closedRecruitment);
        // when & then
        assertThatThrownBy(() -> recruitmentValidator.validateQuestionModifiable(now))
            .isInstanceOf(QuestionModifyNotAllowed.class);
    }

    @Test
    @DisplayName("모집이 진행중이지 않은 경우 예외를 발생시키지 않는다.")
    void doesNotThrowIfModificationAllowed() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Recruitment closedRecruitment = new Recruitment("2025", "fakeId", now.minusDays(5L), now.minusDays(1L));
        recruitmentRepository.save(closedRecruitment);
        // when & then
        assertThatCode(() -> recruitmentValidator.validateQuestionModifiable(now)).doesNotThrowAnyException();
    }
}
