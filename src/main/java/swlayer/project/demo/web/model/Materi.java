package swlayer.project.demo.web.model;


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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "materi")
public class Materi extends DateConfig {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "question_total")
    private int questionTotal;

    @Column(name = "materi")
    private String materi;
    @Column(name = "teacher")
    private String teacher;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "school_id")
    private School school;

    @ToString.Exclude
    @Transient
    private List<Question> question = new ArrayList<>();
}



