package dk.optimize.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Concreting.
 */
@Entity
@Table(name = "concreting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "concreting")
public class Concreting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "last_updated_at")
    private LocalDate lastUpdatedAt;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "mix_design")
    private String mixDesign;

    @Column(name = "slump_flow_test")
    private Integer slumpFlowTest;

    @Column(name = "pouring_rate", precision=10, scale=2)
    private BigDecimal pouringRate;

    @Column(name = "total_casted_volume", precision=10, scale=2)
    private BigDecimal totalCastedVolume;

    @Column(name = "over_consumption")
    private Integer overConsumption;

    @Column(name = "comment")
    private String comment;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    @Column(name = "calculated_cumulative_cls")
    private String calculatedCumulativeCls;

    @Column(name = "calculated_theoretic_cls")
    private String calculatedTheoreticCls;

    @Column(name = "calculated_difference")
    private String calculatedDifference;

    @Column(name = "calculated_percent")
    private String calculatedPercent;

    @Column(name = "sent_back", precision=10, scale=2)
    private BigDecimal sentBack;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @OneToOne(mappedBy = "concreting")
    @JsonIgnore
    private Pile pile;

    @OneToMany(mappedBy = "concreting")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Item> items = new HashSet<>();

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

    public String getMixDesign() {
        return mixDesign;
    }

    public void setMixDesign(String mixDesign) {
        this.mixDesign = mixDesign;
    }

    public Integer getSlumpFlowTest() {
        return slumpFlowTest;
    }

    public void setSlumpFlowTest(Integer slumpFlowTest) {
        this.slumpFlowTest = slumpFlowTest;
    }

    public BigDecimal getPouringRate() {
        return pouringRate;
    }

    public void setPouringRate(BigDecimal pouringRate) {
        this.pouringRate = pouringRate;
    }

    public BigDecimal getTotalCastedVolume() {
        return totalCastedVolume;
    }

    public void setTotalCastedVolume(BigDecimal totalCastedVolume) {
        this.totalCastedVolume = totalCastedVolume;
    }

    public Integer getOverConsumption() {
        return overConsumption;
    }

    public void setOverConsumption(Integer overConsumption) {
        this.overConsumption = overConsumption;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getCalculatedCumulativeCls() {
        return calculatedCumulativeCls;
    }

    public void setCalculatedCumulativeCls(String calculatedCumulativeCls) {
        this.calculatedCumulativeCls = calculatedCumulativeCls;
    }

    public String getCalculatedTheoreticCls() {
        return calculatedTheoreticCls;
    }

    public void setCalculatedTheoreticCls(String calculatedTheoreticCls) {
        this.calculatedTheoreticCls = calculatedTheoreticCls;
    }

    public String getCalculatedDifference() {
        return calculatedDifference;
    }

    public void setCalculatedDifference(String calculatedDifference) {
        this.calculatedDifference = calculatedDifference;
    }

    public String getCalculatedPercent() {
        return calculatedPercent;
    }

    public void setCalculatedPercent(String calculatedPercent) {
        this.calculatedPercent = calculatedPercent;
    }

    public BigDecimal getSentBack() {
        return sentBack;
    }

    public void setSentBack(BigDecimal sentBack) {
        this.sentBack = sentBack;
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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Concreting concreting = (Concreting) o;
        if(concreting.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, concreting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Concreting{" +
            "id=" + id +
            ", createdAt='" + createdAt + "'" +
            ", lastUpdatedAt='" + lastUpdatedAt + "'" +
            ", lastUpdatedBy='" + lastUpdatedBy + "'" +
            ", mixDesign='" + mixDesign + "'" +
            ", slumpFlowTest='" + slumpFlowTest + "'" +
            ", pouringRate='" + pouringRate + "'" +
            ", totalCastedVolume='" + totalCastedVolume + "'" +
            ", overConsumption='" + overConsumption + "'" +
            ", comment='" + comment + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", calculatedCumulativeCls='" + calculatedCumulativeCls + "'" +
            ", calculatedTheoreticCls='" + calculatedTheoreticCls + "'" +
            ", calculatedDifference='" + calculatedDifference + "'" +
            ", calculatedPercent='" + calculatedPercent + "'" +
            ", sentBack='" + sentBack + "'" +
            '}';
    }
}
