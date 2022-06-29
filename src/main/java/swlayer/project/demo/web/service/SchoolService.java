package swlayer.project.demo.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import swlayer.project.demo.web.model.School;

import java.util.List;

public interface SchoolService {
    School createSchool(School school);
    School updateSchool(String id, String adminId, School school);

    Page<School> searchByShoolName(String name, int page, int size);
    Page<School> schoolList(int page, int size);

    School selectRandomSchool();
}
