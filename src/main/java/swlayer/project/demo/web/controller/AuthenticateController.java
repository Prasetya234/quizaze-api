package swlayer.project.demo.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swlayer.project.demo.enggine.response.CommonResponse;
import swlayer.project.demo.enggine.response.ResponseHelper;
import swlayer.project.demo.web.model.User;
import swlayer.project.demo.web.service.UserService;
import swlayer.project.demo.webrequest.dto.AdminRegistrasionRequest;
import swlayer.project.demo.webrequest.dto.UserAuthenticate;
import swlayer.project.demo.webrequest.dto.UserAuthenticateResponse;
import swlayer.project.demo.webrequest.dto.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class AuthenticateController {
    private UserService userService;
    private ModelMapper modelmapper;

    @Autowired
    public AuthenticateController(UserService userService, ModelMapper modelmapper) {
        this.userService = userService;
        this.modelmapper = modelmapper;
    }

    @PostMapping("/login")
    public CommonResponse<UserAuthenticateResponse> authenticate(@RequestBody UserAuthenticate user) {
        return ResponseHelper.successResponse(userService.authenticate(user));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL')")
    @PostMapping("/signup-admin-school/{school_id}")
    public CommonResponse<UserResponse> signupAdmin(@PathVariable("school_id")String schoolId, @RequestBody AdminRegistrasionRequest request) {
        return  ResponseHelper.successResponse(modelmapper.map(userService.createAccountAdminSchool(schoolId, modelmapper.map(request, User.class)), UserResponse.class));
    }

    @PostMapping("/signup-admin")
    public CommonResponse<UserResponse> signupAdminUser(@RequestBody AdminRegistrasionRequest request) {
        return  ResponseHelper.successResponse(modelmapper.map(userService.createAccountAdmin(modelmapper.map(request, User.class)), UserResponse.class));
    }

}
