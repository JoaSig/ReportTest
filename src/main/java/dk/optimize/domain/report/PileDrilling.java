package dk.optimize.domain.report;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Date: 19/02/16
 */
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
