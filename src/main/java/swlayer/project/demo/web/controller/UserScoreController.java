package swlayer.project.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swlayer.project.demo.enggine.response.CommonResponse;
import swlayer.project.demo.enggine.response.ResponseHelper;
import swlayer.project.demo.web.model.UserScore;
import swlayer.project.demo.web.service.UserScoreService;
import swlayer.project.demo.webrequest.dto.UpdateUser;
import swlayer.project.demo.webrequest.dto.UserResponse;
import swlayer.project.demo.webrequest.util.Constants;

import java.util.List;

@RestController
@RequestMapping(Constants.SWLAYEREndPoint.USERSCORE)
public class UserScoreController {

    private UserScoreService userScoreService;

    @Autowired
    public UserScoreController(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/{user_id}")
    public CommonResponse<List<UserScore>> updateUser(@PathVariable("user_id") String user) {
        return ResponseHelper.successResponse(userScoreService.getDataScore(user));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/{user_id}/{materi_id}")
    public CommonResponse<UserScore> updateUser(@PathVariable("user_id") String user, @PathVariable("materi_id") String materi) {
        return ResponseHelper.successResponse(userScoreService.getDataScoreMateri(user, materi));
    }
}
