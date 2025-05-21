package gdgoc.tuk.official.recruitment.service;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.question.exception.QuestionModifyNotAllowed;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RecruitmentValidator {

    private final RecruitmentRepository recruitmentRepository;

    public void validateQuestionModifiable(final LocalDateTime now) {
        if (recruitmentRepository.existsByCloseAtIsAfter(now)) {
            throw new QuestionModifyNotAllowed(ErrorCode.MODIFY_NOT_ALLOWED);
        }
    }

}
