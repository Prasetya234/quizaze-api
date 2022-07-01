package swlayer.project.demo.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import swlayer.project.demo.enggine.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_answer")
public class UserAnswer extends DateConfig {

        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        private String id;

        @Column(name = "answer", nullable = false)
        private String answer;

        @Column(name = "is_correct")
        private boolean isCorrect;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
        @JoinColumn(name = "user_id")
        private User user;

        @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.MERGE)
        @JoinColumn(name = "question_id")
        private Question question;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
        @JoinColumn(name = "school_id")
        private School school;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
        @JoinColumn(name = "materi_id")
        private Materi materi;
}

