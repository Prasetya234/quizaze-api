package swlayer.project.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swlayer.project.demo.enggine.response.CommonResponse;
import swlayer.project.demo.enggine.response.ResponseHelper;
import swlayer.project.demo.web.model.Materi;
import swlayer.project.demo.web.model.Question;
import swlayer.project.demo.web.service.MateriService;
import swlayer.project.demo.web.service.QuestionService;
import swlayer.project.demo.webrequest.dto.CreateQuestion;
import swlayer.project.demo.webrequest.util.Constants;

import java.util.Optional;

@RestController
@RequestMapping(Constants.SWLAYEREndPoint.QUESTION)
public class QuestionController {

    QuestionService questionService;
    MateriService materiService;

    @Autowired
    public QuestionController(QuestionService questionService, MateriService materiService) {
        this.questionService = questionService;
        this.materiService = materiService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL')")
    @PostMapping("/{school_id}/admin")
    public CommonResponse<Materi> create(@PathVariable("school_id") String schoolId, @RequestBody CreateQuestion question) {
        return ResponseHelper.successResponse(questionService.createQuestion(schoolId, question));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL', 'USER')")
    @GetMapping("/materi")
    public CommonResponse<Page<Materi>> getAllMateri(
            @RequestParam(name = "materi", required = false) String materi, @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseHelper.successResponse(materiService.findAllMateri(materi, page, size));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL')")
    @PutMapping("/{materi_id}/admin")
    public CommonResponse<Materi> updateMateri(@PathVariable("materi_id") String materiId, @RequestBody CreateQuestion question) {
        return ResponseHelper.successResponse(materiService.update(materiId, question));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{materi_id}/user")
    public CommonResponse<Materi> showQuestionUser(@PathVariable("materi_id") String materiId) {
        return ResponseHelper.successResponse(materiService.showMateriUser(materiId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/{materi_id}/admin")
    public CommonResponse<Materi> showQuestionAdmin(@PathVariable("materi_id") String materiId) {
        return ResponseHelper.successResponse(materiService.showMateriAdmin(materiId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL', 'USER')")
    @GetMapping("/{materi_id}/{question_id}")
    public CommonResponse<Question> getQuestionId(@PathVariable("materi_id") String materiId, @PathVariable("question_id") String questionId) {
        return ResponseHelper.successResponse(questionService.getQUestionById(questionId, materiId));
    }

}
