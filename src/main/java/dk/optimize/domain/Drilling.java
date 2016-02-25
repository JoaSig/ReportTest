package dk.optimize.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Drilling.
 */
@Entity
@Table(name = "drilling")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "drilling")
public class Drilling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pile_type")
    private String pileType;
    
    @Column(name = "piling_rig_type")
    private String pilingRigType;
    
    @Column(name = "pile_nr")
    private Integer pileNr;
    
    @Column(name = "pile_length", precision=10, scale=2)
    private BigDecimal pileLength;
    
    @Column(name = "diameter", precision=10, scale=2)
    private BigDecimal diameter;
    
    @Column(name = "top_guide_elevation", precision=10, scale=2)
    private BigDecimal topGuideElevation;
    
    @Column(name = "pile_top_level", precision=10, scale=2)
    private BigDecimal pileTopLevel;
    
    @Column(name = "pile_cutoff_elevation", precision=10, scale=2)
    private BigDecimal pileCutoffElevation;
    
    @Column(name = "pile_toe_level", precision=10, scale=2)
    private BigDecimal pileToeLevel;
    
    @Column(name = "casing_deviation", precision=10, scale=2)
    private BigDecimal casingDeviation;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "start_time")
    private LocalDate startTime;
    
    @Column(name = "end_time")
    private LocalDate endTime;
    
    @Column(name = "comment")
    private String comment;
    
    @OneToOne
    private User user;

    @OneToOne(mappedBy = "drilling")
    @JsonIgnore
    private Pile pile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPileType() {
        return pileType;
    }
    
    public void setPileType(String pileType) {
        this.pileType = pileType;
    }

    public String getPilingRigType() {
        return pilingRigType;
    }
    
    public void setPilingRigType(String pilingRigType) {
        this.pilingRigType = pilingRigType;
    }

    public Integer getPileNr() {
        return pileNr;
    }
    
    public void setPileNr(Integer pileNr) {
        this.pileNr = pileNr;
    }

    public BigDecimal getPileLength() {
        return pileLength;
    }
    
    public void setPileLength(BigDecimal pileLength) {
        this.pileLength = pileLength;
    }

    public BigDecimal getDiameter() {
        return diameter;
    }
    
    public void setDiameter(BigDecimal diameter) {
        this.diameter = diameter;
    }

    public BigDecimal getTopGuideElevation() {
        return topGuideElevation;
    }
    
    public void setTopGuideElevation(BigDecimal topGuideElevation) {
        this.topGuideElevation = topGuideElevation;
    }

    public BigDecimal getPileTopLevel() {
        return pileTopLevel;
    }
    
    public void setPileTopLevel(BigDecimal pileTopLevel) {
        this.pileTopLevel = pileTopLevel;
    }

    public BigDecimal getPileCutoffElevation() {
        return pileCutoffElevation;
    }
    
    public void setPileCutoffElevation(BigDecimal pileCutoffElevation) {
        this.pileCutoffElevation = pileCutoffElevation;
    }

    public BigDecimal getPileToeLevel() {
        return pileToeLevel;
    }
    
    public void setPileToeLevel(BigDecimal pileToeLevel) {
        this.pileToeLevel = pileToeLevel;
    }

    public BigDecimal getCasingDeviation() {
        return casingDeviation;
    }
    
    public void setCasingDeviation(BigDecimal casingDeviation) {
        this.casingDeviation = casingDeviation;
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
    
    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pile getPile() {
        return pile;
    }

    public void setPile(Pile pile) {
        this.pile = pile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Drilling drilling = (Drilling) o;
        if(drilling.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, drilling.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Drilling{" +
            "id=" + id +
            ", pileType='" + pileType + "'" +
            ", pilingRigType='" + pilingRigType + "'" +
            ", pileNr='" + pileNr + "'" +
            ", pileLength='" + pileLength + "'" +
            ", diameter='" + diameter + "'" +
            ", topGuideElevation='" + topGuideElevation + "'" +
            ", pileTopLevel='" + pileTopLevel + "'" +
            ", pileCutoffElevation='" + pileCutoffElevation + "'" +
            ", pileToeLevel='" + pileToeLevel + "'" +
            ", casingDeviation='" + casingDeviation + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
