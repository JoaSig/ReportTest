package dk.optimize.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SteelCage.
 */
@Entity
@Table(name = "steel_cage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "steelcage")
public class SteelCage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at")
    private LocalDate createdAt;
    
    @Column(name = "id_cage")
    private String idCage;
    
    @Column(name = "sonic_pipes_compliance")
    private Boolean sonicPipesCompliance;
    
    @Column(name = "water_and_capping_sonic_pipe_filling")
    private Boolean waterAndCappingSonicPipeFilling;
    
    @Column(name = "overlapping_compliance")
    private Boolean overlappingCompliance;
    
    @Column(name = "spacer_position_compliance")
    private Boolean spacerPositionCompliance;
    
    @Column(name = "distance_between_cage_top_and_guide_wall_edge")
    private Boolean distanceBetweenCageTopAndGuideWallEdge;
    
    @Column(name = "verticality_compliance")
    private Boolean verticalityCompliance;
    
    @Column(name = "last_updated_at")
    private LocalDate lastUpdatedAt;
    
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    
    @Column(name = "start_time")
    private LocalDate startTime;
    
    @Column(name = "end_time")
    private LocalDate endTime;
    
    @Column(name = "comment")
    private String comment;
    
    @OneToOne
    private User user;

    @OneToOne(mappedBy = "concreting")
    @JsonIgnore
    private Pile pile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getIdCage() {
        return idCage;
    }
    
    public void setIdCage(String idCage) {
        this.idCage = idCage;
    }

    public Boolean getSonicPipesCompliance() {
        return sonicPipesCompliance;
    }
    
    public void setSonicPipesCompliance(Boolean sonicPipesCompliance) {
        this.sonicPipesCompliance = sonicPipesCompliance;
    }

    public Boolean getWaterAndCappingSonicPipeFilling() {
        return waterAndCappingSonicPipeFilling;
    }
    
    public void setWaterAndCappingSonicPipeFilling(Boolean waterAndCappingSonicPipeFilling) {
        this.waterAndCappingSonicPipeFilling = waterAndCappingSonicPipeFilling;
    }

    public Boolean getOverlappingCompliance() {
        return overlappingCompliance;
    }
    
    public void setOverlappingCompliance(Boolean overlappingCompliance) {
        this.overlappingCompliance = overlappingCompliance;
    }

    public Boolean getSpacerPositionCompliance() {
        return spacerPositionCompliance;
    }
    
    public void setSpacerPositionCompliance(Boolean spacerPositionCompliance) {
        this.spacerPositionCompliance = spacerPositionCompliance;
    }

    public Boolean getDistanceBetweenCageTopAndGuideWallEdge() {
        return distanceBetweenCageTopAndGuideWallEdge;
    }
    
    public void setDistanceBetweenCageTopAndGuideWallEdge(Boolean distanceBetweenCageTopAndGuideWallEdge) {
        this.distanceBetweenCageTopAndGuideWallEdge = distanceBetweenCageTopAndGuideWallEdge;
    }

    public Boolean getVerticalityCompliance() {
        return verticalityCompliance;
    }
    
    public void setVerticalityCompliance(Boolean verticalityCompliance) {
        this.verticalityCompliance = verticalityCompliance;
    }

    public LocalDate getLastUpdatedAt() {
        return lastUpdatedAt;
    }
    
    public void setLastUpdatedAt(LocalDate lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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
        SteelCage steelCage = (SteelCage) o;
        if(steelCage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, steelCage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SteelCage{" +
            "id=" + id +
            ", createdAt='" + createdAt + "'" +
            ", idCage='" + idCage + "'" +
            ", sonicPipesCompliance='" + sonicPipesCompliance + "'" +
            ", waterAndCappingSonicPipeFilling='" + waterAndCappingSonicPipeFilling + "'" +
            ", overlappingCompliance='" + overlappingCompliance + "'" +
            ", spacerPositionCompliance='" + spacerPositionCompliance + "'" +
            ", distanceBetweenCageTopAndGuideWallEdge='" + distanceBetweenCageTopAndGuideWallEdge + "'" +
            ", verticalityCompliance='" + verticalityCompliance + "'" +
            ", lastUpdatedAt='" + lastUpdatedAt + "'" +
            ", lastUpdatedBy='" + lastUpdatedBy + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
