package swlayer.project.demo.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.Materi;

@Repository
public interface MateriRepository extends JpaRepository<Materi, String> {
    @Query(value = "SELECT a.* FROM materi a WHERE lower(a.materi) like lower(concat('%', concat(:materi, '%'))) OR :materi IS NULL", nativeQuery = true)
    Page<Materi> searchMateri(String materi, Pageable pageable);

}
