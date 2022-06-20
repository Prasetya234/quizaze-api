package swlayer.project.demo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {

    List<Question> findByMateriIdAndPublishIsTrueOrderByCreateAtAsc(String materiId);
    @Query(value = "SELECT a.* FROM question a WHERE a.materi_id = :materiId AND a.publish = true ORDER BY RAND()", nativeQuery = true)
    List<Question> findByMateriIdAndPublishIsTrueUser(String materiId);
    Optional<Question> findByIdAndMateriIdAndPublishIsTrue(String questionId, String materiId);
}
