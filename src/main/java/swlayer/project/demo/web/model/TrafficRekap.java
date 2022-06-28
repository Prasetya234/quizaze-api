package swlayer.project.demo.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import swlayer.project.demo.enggine.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "traffic_recap")
public class TrafficRekap extends DateConfig {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "this_date")
    private Date thisDate;

    @Column(name = "visitors")
    private int visitors;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "school_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private School school;
}
