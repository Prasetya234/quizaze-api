package swlayer.project.demo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swlayer.project.demo.web.model.Materi;

@Repository
public interface MateriRepository extends JpaRepository<Materi, String> {
}
