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

@Data
@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
public class Question extends DateConfig {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Lob
    @Column(name = "image")
    private String image;

    @Column(name = "question")
    private String question;

    @Column(name = "answer_true")
    private String answerTrue;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "answer_list")
    private String answerList;

    @Column(name = "count_used")
    private int countUsed;

    @Column(name = "publish")
    private boolean publish;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "school_id")
    private School school;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "materi_id")
    private Materi materi;

    @ToString.Exclude
    @Transient
    private List<String> listAnswer = new ArrayList<>();
}
