package swlayer.project.demo.web.service;

import org.springframework.data.domain.Page;
import swlayer.project.demo.web.model.Materi;
import swlayer.project.demo.webrequest.dto.CreateQuestion;

import java.util.List;

public interface MateriService {
    Materi create(String schoolId, CreateQuestion question);
    Materi showMateriUser(String materiId);

    Materi showMateriAdmin(String materiId);

    Materi update(String materiId, CreateQuestion question);
    Materi getQuestion(String materiId, boolean admin);

    Page<Materi> findAllMateri(int page, int size);
}
