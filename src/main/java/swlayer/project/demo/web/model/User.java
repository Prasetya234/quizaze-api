package swlayer.project.demo.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import swlayer.project.demo.enggine.auditing.DateConfig;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@ToString
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends DateConfig{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "device")
    private String device;

    @Lob
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "blocked")
    private boolean blocked;

    @Column(name = "guest")
    private boolean guest;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "role")
    private String role;

    @ToString.Exclude
    @Transient
    private List<String> roles;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "school_id")
    private School school;

    public List<String> getRoles() {
        List<String> result = List.of(getRole().split(","));
        return result;
    }
}
