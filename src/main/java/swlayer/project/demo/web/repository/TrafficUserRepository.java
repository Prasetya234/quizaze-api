package swlayer.project.demo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.TrafficUser;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TrafficUserRepository extends JpaRepository<TrafficUser, String> {

    Optional<TrafficUser> findByUserId(String userId);
}
