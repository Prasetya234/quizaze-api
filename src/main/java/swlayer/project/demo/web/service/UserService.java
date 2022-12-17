package swlayer.project.demo.web.service;

import swlayer.project.demo.web.model.User;
import swlayer.project.demo.webrequest.dto.*;

import java.util.List;

public interface UserService {
    UserResponse updateUser(String id, UpdateUser user);
    List<UserResponse> showGuest(int page, int size);
    UserResponse getById(String id);
    UserResponse updateSchool(String userId, String schoolId);
    UserAuthenticateResponse authenticate(UserAuthenticate user);

    User createAccountAdminSchool( User user);

    User createAccountAdmin(User user);
}
