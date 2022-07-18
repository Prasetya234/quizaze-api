package swlayer.project.demo.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swlayer.project.demo.enggine.response.CommonResponse;
import swlayer.project.demo.enggine.response.ResponseHelper;
import swlayer.project.demo.web.model.School;
import swlayer.project.demo.web.service.SchoolService;
import swlayer.project.demo.webrequest.dto.SchoolRequest;
import swlayer.project.demo.webrequest.util.Constants;

import java.util.List;

@RestController
@RequestMapping(Constants.SWLAYEREndPoint.REGISTRASION)
public class SchoolRegistrasionController {

    private SchoolService schoolService;
    private ModelMapper modelMapper;

    @Autowired
    public SchoolRegistrasionController(SchoolService schoolService, ModelMapper modelMapper) {
        this.schoolService = schoolService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAuthority('ADMIN_SCHOOL')")
    @PostMapping
    public CommonResponse<School> createSchool(@RequestBody SchoolRequest schoolRequest) {
        return ResponseHelper.successResponse(schoolService.createSchool(modelMapper.map(schoolRequest, School.class)));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL')")
    @PutMapping("/{admin_id}/{school_id}")
    public CommonResponse<School> updateSchool( @PathVariable("school_id") String id,  @PathVariable("admin_id") String adminId, @RequestBody SchoolRequest schoolRequest) {
        return ResponseHelper.successResponse(schoolService.updateSchool(id, adminId, modelMapper.map(schoolRequest, School.class)));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/list")
    public CommonResponse<Page<School>> findAllSchool(@RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        return ResponseHelper.successResponse(schoolService.schoolList(page, size));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/search")
    public CommonResponse<Page<School>> searchSchoolByName(@RequestParam(name = "school") String name, @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        return ResponseHelper.successResponse(schoolService.searchByShoolName(name, page, size));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/{id}")
    public CommonResponse<School> findSchoolById(@RequestParam(name = "id") String id) {
        return ResponseHelper.successResponse(schoolService.findSchoolById(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/random")
    public CommonResponse<School> selectSchoolRandom() {
        return ResponseHelper.successResponse(schoolService.selectRandomSchool());
    }
}
