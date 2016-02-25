package dk.optimize.domain.report;

import dk.optimize.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Pile.
 */
//@Entity
//@Table(name = "pile")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "pile")
public class PileOld implements Serializable {

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
//    @Column(name = "last_updated_at")
//    private LocalDate lastUpdatedAt;
//
//    @Basic
//    @Column(name = "last_updated_by")
//    private String lastUpdatedBy;
//
//    @Basic
//    @Column(name = "next_pile")
//    private long nextPile;
//
//    @Column(name = "prev_pile")
//    private long prevPile;
//
//    @ManyToOne
//    @JoinColumn(name = "project_info_id")
//    private ProjectInfoOld projectInfo;
//
//    @OneToOne
//    @JoinColumn(name="user_id")
//    private User user;
//
//    @OneToOne
//    @JoinColumn(name="user_id")
//    private ConcretingOld concreting;
//
//    @OneToOne
//    @JoinColumn(name="user_id")
//    private DrillingOld drilling;
//
//    @OneToOne
//    @JoinColumn(name="user_id")
//    private SteelCageOld steelCage;
//
//    @Column(name = "comment")
//    private String comment;
//
//    public ProjectInfoOld getProjectInfo() {
//        return projectInfo;
//    }
//
//    public void setProjectInfo(ProjectInfoOld projectInfo) {
//        this.projectInfo = projectInfo;
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
//    public ConcretingOld getConcreting() {
//        return concreting;
//    }
//
//    public void setConcreting(ConcretingOld concreting) {
//        this.concreting = concreting;
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
//    public DrillingOld getDrilling() {
//        return drilling;
//    }
//
//    public void setDrilling(DrillingOld drilling) {
//        this.drilling = drilling;
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
//    public long getNextPile() {
//        return nextPile;
//    }
//
//    public void setNextPile(long nextPile) {
//        this.nextPile = nextPile;
//    }
//
//    public long getPrevPile() {
//        return prevPile;
//    }
//
//    public void setPrevPile(long prevPile) {
//        this.prevPile = prevPile;
//    }
//
////    public ProjectInfo getProjectInfo() {
////        return projectInfo;
////    }
////
////    public void setProjectInfo(ProjectInfo projectInfo) {
////        this.projectInfo = projectInfo;
////    }
//
//    public SteelCageOld getSteelCage() {
//        return steelCage;
//    }
//
//    public void setSteelCage(SteelCageOld steelCage) {
//        this.steelCage = steelCage;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
