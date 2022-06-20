package swlayer.project.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swlayer.project.demo.enggine.response.CommonResponse;
import swlayer.project.demo.enggine.response.ResponseHelper;
import swlayer.project.demo.web.model.UserAnswer;
import swlayer.project.demo.web.service.UserAnswerService;
import swlayer.project.demo.webrequest.dto.AnswerData;
import swlayer.project.demo.webrequest.util.Constants;

@RestController
@RequestMapping(Constants.SWLAYEREndPoint.QUESTION)
public class AnswerController {

    private UserAnswerService userAnswerService;

    @Autowired
    public AnswerController(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL', 'USER')")
    @PostMapping("{question_id}/answer")
    public CommonResponse<UserAnswer> answerController(@PathVariable("question_id") String question, @RequestBody AnswerData answer) {
        return ResponseHelper.successResponse(userAnswerService.answerQuestion(question, answer));
    }
}
