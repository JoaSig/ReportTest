package dk.optimize.domain.report;

import dk.optimize.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Date: 19/02/16
 */
//@Entity
//@Table(name = "drilling")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "drilling")
public class DrillingOld implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "pile_type")
//    private String pileType;
//
//    @Column(name = "piling_rig_type")
//    private String pilingRigType;
//
//    @Column(name = "pile_nr")
//    private Integer pileNr;
//
//    @Column(name = "pile_length", precision = 10, scale = 2)
//    private BigDecimal pileLength;
//
//    @Column(name = "diameter", precision = 10, scale = 2)
//    private BigDecimal diameter;
//
//    @Column(name = "top_guide_elevation", precision = 10, scale = 2)
//    private BigDecimal topGuideElevation;
//
//    @Column(name = "pile_top_level", precision = 10, scale = 2)
//    private BigDecimal pileTopLevel;
//
//    @Column(name = "pile_cutoff_elevation", precision = 10, scale = 2)
//    private BigDecimal pileCutoffElevation;
//
//    @Column(name = "pile_toe_level", precision = 10, scale = 2)
//    private BigDecimal pileToeLevel;
//
//    @Column(name = "casing_deviation", precision = 10, scale = 2)
//    private BigDecimal casingDeviation;
//
//    @Column(name = "start_date")
//    private LocalDate startDate;
//
//    @Column(name = "end_date")
//    private LocalDate endDate;
//
//    @Column(name = "start_time")
//    private Timestamp startTime;
//
//    @Column(name = "end_time")
//    private Timestamp endTime;
//
//    @OneToOne(mappedBy="drilling")
//    private PileOld pile;
//
//    @Column(name = "comment")
//    private String comment;
//
//    @OneToOne
//    @JoinColumn(name = "creator_id")
//    private User creator;
//
//    public PileOld getPile() {
//        return pile;
//    }
//
//    public void setPile(PileOld pile) {
//        this.pile = pile;
//    }
//
//    public String getPilingRigType() {
//        return pilingRigType;
//    }
//
//    public void setPilingRigType(String pilingRigType) {
//        this.pilingRigType = pilingRigType;
//    }
//
//    public BigDecimal getCasingDeviation() {
//        return casingDeviation;
//    }
//
//    public void setCasingDeviation(BigDecimal casingDeviation) {
//        this.casingDeviation = casingDeviation;
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
//    public User getCreator() {
//        return creator;
//    }
//
//    public void setCreator(User creator) {
//        this.creator = creator;
//    }
//
//    public BigDecimal getDiameter() {
//        return diameter;
//    }
//
//    public void setDiameter(BigDecimal diameter) {
//        this.diameter = diameter;
//    }
//
//    public LocalDate getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
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
//    public BigDecimal getPileCutoffElevation() {
//        return pileCutoffElevation;
//    }
//
//    public void setPileCutoffElevation(BigDecimal pileCutoffElevation) {
//        this.pileCutoffElevation = pileCutoffElevation;
//    }
//
//    public BigDecimal getPileLength() {
//        return pileLength;
//    }
//
//    public void setPileLength(BigDecimal pileLength) {
//        this.pileLength = pileLength;
//    }
//
//    public Integer getPileNr() {
//        return pileNr;
//    }
//
//    public void setPileNr(Integer pileNr) {
//        this.pileNr = pileNr;
//    }
//
//    public BigDecimal getPileToeLevel() {
//        return pileToeLevel;
//    }
//
//    public void setPileToeLevel(BigDecimal pileToeLevel) {
//        this.pileToeLevel = pileToeLevel;
//    }
//
//    public BigDecimal getPileTopLevel() {
//        return pileTopLevel;
//    }
//
//    public void setPileTopLevel(BigDecimal pileTopLevel) {
//        this.pileTopLevel = pileTopLevel;
//    }
//
//    public String getPileType() {
//        return pileType;
//    }
//
//    public void setPileType(String pileType) {
//        this.pileType = pileType;
//    }
//
//    public LocalDate getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
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
//    public BigDecimal getTopGuideElevation() {
//        return topGuideElevation;
//    }
//
//    public void setTopGuideElevation(BigDecimal topGuideElevation) {
//        this.topGuideElevation = topGuideElevation;
//    }
}
