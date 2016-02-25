package dk.optimize.domain.report;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Date: 24/02/16
 */
//@Entity
//@Table(name = "steel_cage")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "steelcage")
public class SteelCageOld implements Serializable {
//    @PrePersist
//    public void onCreate() {
//        if (getCreatedAt() == null)
//            setCreatedAt(LocalDate.now());
//    }
//
//    @PreUpdate
//    public void onUpdate() {
//        setLastUpdatedAt(LocalDate.now());
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Basic
//    @Column(name = "created_at")
//    private LocalDate createdAt;
//
//    @Basic
//    @Column(name = "id_cage")
//    private String idCage;
//
//    @Basic
//    @Column(name = "sonic_pipes_compliance")
//    private Boolean sonicPipesCompliance;
//
//    @Basic
//    @Column(name = "water_and_capping_sonic_pipe_filling")
//    private Boolean waterAndCappingSonicPipeFilling;
//
//    @Basic
//    @Column(name = "overlapping_compliance")
//    private Boolean overlappingCompliance;
//
//    @Basic
//    @Column(name = "spacer_position_compliance")
//    private Boolean spacerPositionCompliance;
//
//    @Basic
//    @Column(name = "distance_between_cage_top_and_guide_wall_edge")
//    private Boolean distanceBetweenCageTopAndGuideWallEdge;
//
//    @Basic
//    @Column(name = "verticality_compliance")
//    private Boolean verticalityCompliance;
//
//    @Basic
//    @Column(name = "last_updated_at")
//    private LocalDate lastUpdatedAt;
//
//    @Basic
//    @Column(name = "last_updated_by")
//    private String lastUpdatedBy;
//
//    @Column(name = "start_time")
//    private Timestamp startTime;
//
//    @Column(name = "end_time")
//    private Timestamp endTime;
//
//    @Column(name = "comment")
//    private String comment;
//
//    @OneToOne(mappedBy="steelCage")
//    private PileOld pile;
//
//    public PileOld getPile() {
//        return pile;
//    }
//
//    public void setPile(PileOld pile) {
//        this.pile = pile;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    public LocalDate getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDate createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Boolean getDistanceBetweenCageTopAndGuideWallEdge() {
//        return distanceBetweenCageTopAndGuideWallEdge;
//    }
//
//    public void setDistanceBetweenCageTopAndGuideWallEdge(Boolean distanceBetweenCageTopAndGuideWallEdge) {
//        this.distanceBetweenCageTopAndGuideWallEdge = distanceBetweenCageTopAndGuideWallEdge;
//    }
//
//    public Timestamp getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Timestamp endTime) {
//        this.endTime = endTime;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getIdCage() {
//        return idCage;
//    }
//
//    public void setIdCage(String idCage) {
//        this.idCage = idCage;
//    }
//
//    public LocalDate getLastUpdatedAt() {
//        return lastUpdatedAt;
//    }
//
//    public void setLastUpdatedAt(LocalDate lastUpdatedAt) {
//        this.lastUpdatedAt = lastUpdatedAt;
//    }
//
//    public String getLastUpdatedBy() {
//        return lastUpdatedBy;
//    }
//
//    public void setLastUpdatedBy(String lastUpdatedBy) {
//        this.lastUpdatedBy = lastUpdatedBy;
//    }
//
//    public Boolean getOverlappingCompliance() {
//        return overlappingCompliance;
//    }
//
//    public void setOverlappingCompliance(Boolean overlappingCompliance) {
//        this.overlappingCompliance = overlappingCompliance;
//    }
//
//    public Boolean getSonicPipesCompliance() {
//        return sonicPipesCompliance;
//    }
//
//    public void setSonicPipesCompliance(Boolean sonicPipesCompliance) {
//        this.sonicPipesCompliance = sonicPipesCompliance;
//    }
//
//    public Boolean getSpacerPositionCompliance() {
//        return spacerPositionCompliance;
//    }
//
//    public void setSpacerPositionCompliance(Boolean spacerPositionCompliance) {
//        this.spacerPositionCompliance = spacerPositionCompliance;
//    }
//
//    public Timestamp getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Timestamp startTime) {
//        this.startTime = startTime;
//    }
//
//    public Boolean getVerticalityCompliance() {
//        return verticalityCompliance;
//    }
//
//    public void setVerticalityCompliance(Boolean verticalityCompliance) {
//        this.verticalityCompliance = verticalityCompliance;
//    }
//
//    public Boolean getWaterAndCappingSonicPipeFilling() {
//        return waterAndCappingSonicPipeFilling;
//    }
//
//    public void setWaterAndCappingSonicPipeFilling(Boolean waterAndCappingSonicPipeFilling) {
//        this.waterAndCappingSonicPipeFilling = waterAndCappingSonicPipeFilling;
//    }
}
