package swlayer.project.demo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.UserAnswer;

import java.util.Optional;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, String> {

    Optional<UserAnswer> findByQuestionIdAndUserId(String questionId, String userId);

    @Query(value = "SELECT a.* FROM user_answer a WHERE a.materi_id = :materiId AND a.user_id = :userId LIMIT 1", nativeQuery = true)
    Optional<UserAnswer> findByMateriId(String materiId,String userId);
}
