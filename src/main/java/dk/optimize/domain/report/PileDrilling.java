package dk.optimize.domain.report;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Date: 19/02/16
 */
@Entity
@Table(name = "pile_drilling")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pileconcreting")
public class PileDrilling  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "project_drilling_depth")
    private String projectDrillingDepth;

    @Column(name = "drilling_start_date")
    private Integer drillingStartDate;

    @Column(name = "drilling_end_date")
    private Integer drillingEndDate;

    @Column(name = "drilling_start_time")
    private Integer drillingStartTime;



}
