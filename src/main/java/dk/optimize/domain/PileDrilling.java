package dk.optimize.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PileDrilling.
 */
@Entity
@Table(name = "pile_drilling")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "piledrilling")
public class PileDrilling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "drilling_machine")
    private String drillingMachine;
    
    @Column(name = "project_drilling_depth", precision=10, scale=2)
    private BigDecimal projectDrillingDepth;
    
    @Column(name = "drilling_effective_depth", precision=10, scale=2)
    private BigDecimal drillingEffectiveDepth;
    
    @Column(name = "drilling_start_date")
    private LocalDate drillingStartDate;
    
    @Column(name = "drilling_end_date")
    private LocalDate drillingEndDate;
    
    @Column(name = "drilling_start_time")
    private String drillingStartTime;
    
    @Column(name = "drilling_end_time")
    private String drillingEndTime;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrillingMachine() {
        return drillingMachine;
    }
    
    public void setDrillingMachine(String drillingMachine) {
        this.drillingMachine = drillingMachine;
    }

    public BigDecimal getProjectDrillingDepth() {
        return projectDrillingDepth;
    }
    
    public void setProjectDrillingDepth(BigDecimal projectDrillingDepth) {
        this.projectDrillingDepth = projectDrillingDepth;
    }

    public BigDecimal getDrillingEffectiveDepth() {
        return drillingEffectiveDepth;
    }
    
    public void setDrillingEffectiveDepth(BigDecimal drillingEffectiveDepth) {
        this.drillingEffectiveDepth = drillingEffectiveDepth;
    }

    public LocalDate getDrillingStartDate() {
        return drillingStartDate;
    }
    
    public void setDrillingStartDate(LocalDate drillingStartDate) {
        this.drillingStartDate = drillingStartDate;
    }

    public LocalDate getDrillingEndDate() {
        return drillingEndDate;
    }
    
    public void setDrillingEndDate(LocalDate drillingEndDate) {
        this.drillingEndDate = drillingEndDate;
    }

    public String getDrillingStartTime() {
        return drillingStartTime;
    }
    
    public void setDrillingStartTime(String drillingStartTime) {
        this.drillingStartTime = drillingStartTime;
    }

    public String getDrillingEndTime() {
        return drillingEndTime;
    }
    
    public void setDrillingEndTime(String drillingEndTime) {
        this.drillingEndTime = drillingEndTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PileDrilling pileDrilling = (PileDrilling) o;
        if(pileDrilling.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pileDrilling.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PileDrilling{" +
            "id=" + id +
            ", drillingMachine='" + drillingMachine + "'" +
            ", projectDrillingDepth='" + projectDrillingDepth + "'" +
            ", drillingEffectiveDepth='" + drillingEffectiveDepth + "'" +
            ", drillingStartDate='" + drillingStartDate + "'" +
            ", drillingEndDate='" + drillingEndDate + "'" +
            ", drillingStartTime='" + drillingStartTime + "'" +
            ", drillingEndTime='" + drillingEndTime + "'" +
            '}';
    }
}
