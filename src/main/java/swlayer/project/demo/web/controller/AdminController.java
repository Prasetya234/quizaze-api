package swlayer.project.demo.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swlayer.project.demo.enggine.response.CommonResponse;
import swlayer.project.demo.enggine.response.ResponseHelper;
import swlayer.project.demo.web.service.UserService;
import swlayer.project.demo.webrequest.dto.UserResponse;
import swlayer.project.demo.webrequest.util.Constants;

import java.util.List;

@RestController
@RequestMapping(Constants.SWLAYEREndPoint.ADMIN)
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADMIN_SCHOOL')")
    @GetMapping("/guest/list")
    public CommonResponse<List<UserResponse>> showGuestUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseHelper.successResponse(userService.showGuest(page, size));
    }

}
