package swlayer.project.demo.web.service;

import org.springframework.data.domain.Page;
import swlayer.project.demo.web.model.School;

public interface SchoolService {
    School createSchool(School school);
    School updateSchool(String id, String adminId, School school);
    School findSchoolById(String id);
    Page<School> searchByShoolName(String name, int page, int size);
    Page<School> schoolList(int page, int size);

    School selectRandomSchool();
}
