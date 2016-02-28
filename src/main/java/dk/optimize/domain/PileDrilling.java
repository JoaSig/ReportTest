package dk.optimize.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @Column(name = "project_depth", precision = 10, scale = 2)
    private BigDecimal projectDepth;

    @Column(name = "effective_depth", precision = 10, scale = 2)
    private BigDecimal effectiveDepth;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    @Column(name = "drilling_id")
    private Long drillingId;

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

    public BigDecimal getProjectDepth() {
        return projectDepth;
    }

    public void setProjectDepth(BigDecimal projectDepth) {
        this.projectDepth = projectDepth;
    }

    public BigDecimal getEffectiveDepth() {
        return effectiveDepth;
    }

    public void setEffectiveDepth(BigDecimal effectiveDepth) {
        this.effectiveDepth = effectiveDepth;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate StartTime) {
        this.startTime = StartTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate EndTime) {
        this.endTime = EndTime;
    }

    public Long getDrillingId() {
        return drillingId;
    }

    public void setDrillingId(Long drillingId) {
        this.drillingId = drillingId;
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
        if (pileDrilling.id == null || id == null) {
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
            ", projectDepth='" + projectDepth + "'" +
            ", effectiveDepth='" + effectiveDepth + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", drillingId='" + drillingId + "'" +
            '}';
    }
}
