package gdgoc.tuk.official.generationmember.repository;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenerationMemberRepository
        extends JpaRepository<GenerationMember, Long>, GenerationMemberRepositorySearch {

    List<GenerationMember> findByGeneration(String generation);

    boolean existsByAccountsAndGeneration(final Accounts accounts, final String generation);

    Optional<GenerationMember> findTopByAccountsOrderByCreatedAt(Accounts accounts);
}
