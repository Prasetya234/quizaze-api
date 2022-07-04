package swlayer.project.demo.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.TrafficRekap;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TrafficRecapRepository extends JpaRepository<TrafficRekap, String> {

    @Query(value = "SELECT  a.* FROM traffic_recap a WHERE  a.school_id = :schoolId AND this_date = :thisDate", nativeQuery = true)
    Optional<TrafficRekap> findSchoolDate(String schoolId, Date thisDate);

    @Query(value = "SELECT  a.* FROM traffic_recap a WHERE  a.school_id = :school ORDER BY a.this_date DESC LIMIT 1", nativeQuery = true)
    Optional<TrafficRekap> findByTrafficSchool(String school);

    @Query(value = "SELECT  b.* FROM traffic_recap b WHERE  b.school_id = :schl ORDER BY b.this_date", nativeQuery = true)
    Page<TrafficRekap> findBySchoolId(String schl, Pageable pageable);
}
