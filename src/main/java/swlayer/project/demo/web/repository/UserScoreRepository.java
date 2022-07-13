package swlayer.project.demo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.UserScore;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, String> {

    Optional<UserScore> findByUserIdAndMateriId(String userId, String materiId);
    Optional<UserScore> findByUserId(String userId);
    Optional<UserScore> findByUserIdAndMatriId(String userId, String materiId);

}
