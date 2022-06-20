package swlayer.project.demo.webrequest.dto;

import lombok.Getter;
import lombok.Setter;
import swlayer.project.demo.enggine.auditing.DateConfig;

@Getter
@Setter
public class UserAuthenticateResponse {
    private String id;
    private String jwt;
    private UserResponse user;
}
