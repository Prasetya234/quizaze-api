package swlayer.project.demo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.UserAnswer;

import java.util.Optional;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, String> {

    Optional<UserAnswer> findByQuestionId(String id);

}
