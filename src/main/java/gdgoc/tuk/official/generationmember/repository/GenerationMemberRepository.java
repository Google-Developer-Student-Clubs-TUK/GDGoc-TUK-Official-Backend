package gdgoc.tuk.official.generationmember.repository;

import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationMemberRepository extends JpaRepository<GenerationMember, Long> {}
