package gdgoc.tuk.official.generationmember.repository;

import static gdgoc.tuk.official.generationmember.domain.QGenerationMember.*;
import static java.util.Objects.*;
import static org.springframework.util.StringUtils.*;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.dto.MemberSearchCond;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class GenerationMemberRepositorySearchImpl implements GenerationMemberRepositorySearch {

    private final JPAQueryFactory queryFactory;

    public GenerationMemberRepositorySearchImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<GenerationMember> findByCondition(final MemberSearchCond memberSearchCond,
        final Pageable pageable) {
        List<GenerationMember> result = queryFactory.select(generationMember)
            .from(generationMember)
            .leftJoin(generationMember.accounts)
            .where(fieldEq(memberSearchCond.field()), enrollmentEq(memberSearchCond.enrollmentStatus()),
                nameEq(memberSearchCond.name()), generationEq(memberSearchCond.generation()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl(result,pageable,result.size());
    }

    private Predicate generationEq(final String generation) {
        return hasText(generation) ? generationMember.generation.eq(generation) : null;
    }

    private Predicate nameEq(final String name) {
        return hasText(name) ? generationMember.name.eq(name) : null;
    }

    private Predicate enrollmentEq(final EnrollmentStatus enrollmentStatus) {
        return nonNull(enrollmentStatus) ?
            generationMember.enrollmentStatus.eq(enrollmentStatus) : null;
    }

    private Predicate fieldEq(final Field field) {
        return nonNull(field) ? generationMember.field.eq(field) : null;
    }
}
