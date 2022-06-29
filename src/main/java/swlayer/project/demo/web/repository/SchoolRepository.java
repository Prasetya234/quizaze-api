package swlayer.project.demo.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.School;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {

    @Query(value = "SELECT s.* FROM school s WHERE lower(s.name) like lower(concat('%', concat(:name, '%')))", nativeQuery = true)
    Page<School> searchSchoolBy(String name, Pageable pageable);

    @Query(value = "SELECT a.* FROM school a WHERE a.id != :schoolId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<School> selectRandomSchool(String schoolId);
}
