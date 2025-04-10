package gdgoc.tuk.official.generationmember.repository;

import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.dto.MemberManagementResponse;
import gdgoc.tuk.official.generationmember.dto.MemberSearchCond;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenerationMemberRepositorySearch {
    Page<GenerationMember> findByCondition(MemberSearchCond condition,
        Pageable pageable);
}
