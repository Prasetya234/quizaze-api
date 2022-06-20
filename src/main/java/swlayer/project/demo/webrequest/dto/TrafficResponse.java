package swlayer.project.demo.webrequest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import swlayer.project.demo.enggine.auditing.DateConfig;

@Getter
@Setter
public class TrafficResponse extends DateConfig {

    private String id;
    private int visitors;
    private String jwt;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserResponse user;

}
