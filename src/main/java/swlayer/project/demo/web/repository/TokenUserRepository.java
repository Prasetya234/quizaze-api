package swlayer.project.demo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.TokenUser;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenUserRepository extends JpaRepository<TokenUser, String> {

    @Query(value = "SELECT a.* FROM token_user a WHERE a.user_id = :id", nativeQuery = true)
    TokenUser findByUser(String id);

    TokenUser findByToken(String token);
    @Query(value = "SELECT a.* FROM token_user a WHERE a.token = :token AND a.expired_date > :expired", nativeQuery = true)
    Optional<TokenUser> findByToken(String token, Date expired);
}
