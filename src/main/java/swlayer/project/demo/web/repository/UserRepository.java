package swlayer.project.demo.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT a.* FROM users a WHERE a.id = :id AND a.school_id = :schoolId AND a.blocked = false", nativeQuery = true)
    Optional<User> findBySchoolId(String id, String schoolId);
    Page<User> findByGuestIsTrueAndBlockedIsFalse(Pageable paging);
    Optional<User> findByUsernameAndBlockedIsFalse(String username);

    Optional<User> findByDevice(String device);
}
