package swlayer.project.demo.web.serviceImplements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swlayer.project.demo.enggine.data.AuthenticationFacade;
import swlayer.project.demo.enggine.exception.BussinesException;
import swlayer.project.demo.enggine.exception.NotFoundException;
import swlayer.project.demo.web.model.School;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.repository.SchoolRepository;
import swlayer.project.demo.web.repository.UserRepository;
import swlayer.project.demo.web.service.SchoolService;

import java.util.List;

@Service
@Slf4j
public class SchoolServiceImpl extends AuthenticationFacade implements SchoolService {

    private SchoolRepository schoolRepository;
    private UserRepository userRepository;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, UserRepository userRepository) {
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public School createSchool(School school) {
        return schoolRepository.save(school);
    }

    @Transactional
    @Override
    public School updateSchool(String id, String adminId, School school) {
        School edit = schoolRepository.findById(id).orElseThrow(() -> new NotFoundException("School id " + id + " not found"));
        User user = userRepository.findBySchoolId(adminId, id).orElseThrow(() -> new BussinesException("User id cannot admin in this school"));
        edit.setAddress(school.getAddress());
        edit.setHeadMaster(school.getHeadMaster());
        edit.setPhoneNumber(school.getPhoneNumber());
        edit.setName(school.getName());
        edit.setUpdateBy(user.getUsername());
        return schoolRepository.save(edit);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<School> searchByShoolName(String name, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return schoolRepository.searchSchoolBy(name, paging);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<School> schoolList(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return schoolRepository.findAll(paging);
    }

    @Transactional
    @Override
    public School selectRandomSchool() {
        User user = userRepository.findByUsernameAndBlockedIsFalse(getAuthentication().getName()).orElseThrow(() -> new BussinesException("User not found"));
        School school = schoolRepository.selectRandomSchool(user.getSchool().equals(null) ? "" : user.getSchool().getId()).orElseThrow(() -> new NotFoundException("Random not running"));
        user.setSchool(school);
        userRepository.save(user);
        return school;
    }
}
