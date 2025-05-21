package gdgoc.tuk.official.answer.service;

import static org.junit.jupiter.api.Assertions.*;

import gdgoc.tuk.official.answer.dto.AnswerListRequest;
import gdgoc.tuk.official.answer.dto.MemberProfile;
import gdgoc.tuk.official.answer.repository.AnswerRepository;
import gdgoc.tuk.official.applicant.service.ApplicantService;
import gdgoc.tuk.official.applicant.service.ApplicantValidator;
import gdgoc.tuk.official.config.TestConfig;
import gdgoc.tuk.official.google.service.SpreadSheetsService;
import gdgoc.tuk.official.recruitment.service.RecruitmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(TestConfig.class)
class AnswerServiceTest {

    @MockitoBean
    private SpreadSheetsService spreadSheetsService;
    @Autowired private ApplicantService applicantService;
    @Autowired private ApplicantValidator applicantValidator;
    @Autowired private AnswerRepository answerRepository;
    @Autowired private RecruitmentService recruitmentService;
    @Autowired private AnswerService answerService;

//    @Test
//    @DisplayName("중복된 지원은 예외를 발생시킨다.")
//    void throwIfDuplicatedApplication() {
//        // given
//        new AnswerListRequest(new MemberProfile(),)
//        answerService.apply()
//        // when
//
//        // then
//    }
}
