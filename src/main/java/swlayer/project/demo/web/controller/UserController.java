package swlayer.project.demo.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swlayer.project.demo.webrequest.dto.UpdateUser;
import swlayer.project.demo.enggine.response.CommonResponse;
import swlayer.project.demo.enggine.response.ResponseHelper;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.service.UserService;
import swlayer.project.demo.webrequest.dto.UserResponse;
import swlayer.project.demo.webrequest.util.Constants;

@RestController
@RequestMapping(Constants.SWLAYEREndPoint.USER)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ADMIN_SCHOOL')")
    @PutMapping("/{user_id}")
    public CommonResponse<UserResponse> updateUser(@PathVariable("user_id") String id, @RequestBody UpdateUser user) {
        return ResponseHelper.successResponse(userService.updateUser(id, user));

    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/{user_id}")
    public CommonResponse<UserResponse> getUserId(@PathVariable("user_id") String id) {
        return  ResponseHelper.successResponse(userService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ADMIN_SCHOOL')")
    @PutMapping("/{user_id}/update-school/{school_id}")
    public CommonResponse<UserResponse> getUserId(@PathVariable("user_id") String user, @PathVariable("school_id") String school) {
        return  ResponseHelper.successResponse(userService.updateSchool(user, school));
    }
}
