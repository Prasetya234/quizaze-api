package swlayer.project.demo.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "this_date")
    private Date thisDate;

    @Column(name = "visitors")
    private int visitors;

    @JoinColumn(name = "school_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private School school;
}
