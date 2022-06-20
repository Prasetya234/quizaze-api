package swlayer.project.demo.webrequest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import swlayer.project.demo.enggine.auditing.DateConfig;
import swlayer.project.demo.web.model.School;

import java.util.List;

@Getter
@Setter
public class UserResponse extends DateConfig {
    private String id;
    private String avatar;
    private String username;
    private String email;
    private List<String> roles;
    private boolean guest;
    private int point;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private School school;
}
