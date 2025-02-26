package gdgoc.tuk.official.questionorder.repository;

import gdgoc.tuk.official.questionorder.domain.QuestionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionOrderRepository extends JpaRepository<QuestionOrder, Long> {
    
    
}
