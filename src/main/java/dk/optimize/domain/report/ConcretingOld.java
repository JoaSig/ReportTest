package dk.optimize.domain.report;

import dk.optimize.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Concreting
 */
//@Entity
//@Table(name = "concreting")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "concreting")
public class ConcretingOld implements Serializable {

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
//    @Column(name = "mix_design")
//    private String mixDesign;
//
//    @Column(name = "slump_flow_test")
//    private Integer slumpFlowTest;
//
//    @Column(name = "pouring_rate", precision=10, scale=2)
//    private BigDecimal pouringRate;
//
//    @Column(name = "total_casted_volume", precision=10, scale=2)
//    private BigDecimal totalCastedVolume;
//
//    @Column(name = "over_consumption")
//    private Integer overConsumption;
//
//    @Column(name = "comment")
//    private String comment;
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
//    @Column(name = "calculated_cumulative_cls")
//    private String calculatedCumulativeCls;
//
//    @Column(name = "calculated_theoretic_cls")
//    private String calculatedTheoreticCls;
//
//    @Column(name = "calculated_difference")
//    private String calculatedDifference;
//
//    @Column(name = "calculated_percent")
//    private String calculatedPercent;
//
//    @Column(name = "sent_back", precision = 10, scale = 2)
//    private BigDecimal sentBack;
//
//    @OneToOne(mappedBy="concreting")
//    private PileOld pile;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToMany(mappedBy = "concreting", cascade = CascadeType.ALL)
//    private Set<ItemOld> items = new HashSet<>();
//
//    public ConcretingOld() {
//    }
//
//    public String getCalculatedCumulativeCls() {
//        return calculatedCumulativeCls;
//    }
//
//    public void setCalculatedCumulativeCls(String calculatedCumulativeCls) {
//        this.calculatedCumulativeCls = calculatedCumulativeCls;
//    }
//
//    public String getCalculatedDifference() {
//        return calculatedDifference;
//    }
//
//    public void setCalculatedDifference(String calculatedDifference) {
//        this.calculatedDifference = calculatedDifference;
//    }
//
//    public String getCalculatedPercent() {
//        return calculatedPercent;
//    }
//
//    public void setCalculatedPercent(String calculatedPercent) {
//        this.calculatedPercent = calculatedPercent;
//    }
//
//    public String getCalculatedTheoreticCls() {
//        return calculatedTheoreticCls;
//    }
//
//    public void setCalculatedTheoreticCls(String calculatedTheoreticCls) {
//        this.calculatedTheoreticCls = calculatedTheoreticCls;
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
//    public Set<ItemOld> getItems() {
//        return items;
//    }
//
//    public void setItems(Set<ItemOld> items) {
//        this.items = items;
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
//    public String getMixDesign() {
//        return mixDesign;
//    }
//
//    public void setMixDesign(String mixDesign) {
//        this.mixDesign = mixDesign;
//    }
//
//    public Integer getOverConsumption() {
//        return overConsumption;
//    }
//
//    public void setOverConsumption(Integer overConsumption) {
//        this.overConsumption = overConsumption;
//    }
//
//    public BigDecimal getPouringRate() {
//        return pouringRate;
//    }
//
//    public void setPouringRate(BigDecimal pouringRate) {
//        this.pouringRate = pouringRate;
//    }
//
//    public BigDecimal getSentBack() {
//        return sentBack;
//    }
//
//    public void setSentBack(BigDecimal sentBack) {
//        this.sentBack = sentBack;
//    }
//
//    public Integer getSlumpFlowTest() {
//        return slumpFlowTest;
//    }
//
//    public void setSlumpFlowTest(Integer slumpFlowTest) {
//        this.slumpFlowTest = slumpFlowTest;
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
//    public BigDecimal getTotalCastedVolume() {
//        return totalCastedVolume;
//    }
//
//    public void setTotalCastedVolume(BigDecimal totalCastedVolume) {
//        this.totalCastedVolume = totalCastedVolume;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public PileOld getPile() {
//        return pile;
//    }
//
//    public void setPile(PileOld pile) {
//        this.pile = pile;
//    }
}
