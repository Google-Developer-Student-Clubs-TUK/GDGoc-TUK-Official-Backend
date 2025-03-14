package gdgoc.tuk.official.generationmember.repository;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationMemberRepository extends JpaRepository<GenerationMember, Long> {

    List<GenerationMember> findByGeneration(String generation);

    boolean existsByAccountsAndGeneration(final Accounts accounts, final String generation);
}
