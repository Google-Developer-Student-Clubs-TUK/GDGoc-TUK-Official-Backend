package gdgoc.tuk.official.recruitment.service;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.question.exception.QuestionModifyNotAllowed;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.exception.RecruitmentNotExistException;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RecruitmentTimeService {

    private final RecruitmentRepository recruitmentRepository;

    public Recruitment getOnGoingRecruitment(final LocalDateTime now) {
        return recruitmentRepository
                .findByBetweenOpenAtAndCloseAt(now)
                .orElseThrow(
                        () ->
                                new RecruitmentNotExistException(
                                        ErrorCode.ON_GOING_RECRUITMENT_NOT_FOUND));
    }

    public void checkQuestionModifiable(final LocalDateTime now) {
        if (recruitmentRepository.existsByCloseAtIsAfter(now)) {
            throw new QuestionModifyNotAllowed(ErrorCode.MODIFY_NOT_ALLOWED);
        }
    }
}
