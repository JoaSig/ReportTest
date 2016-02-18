package dk.optimize.domain.report;

import dk.optimize.domain.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PileConcreting.
 */
//@Entity
//@Table(name = "pile_concreting")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "pileconcreting")
public class PileConcretingOld implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "mix_design")
//    private String mixDesign;
//
//    @Column(name = "slump_1")
//    private Integer slump1;
//
//    @Column(name = "slump_2")
//    private Integer slump2;
//
//    @Column(name = "slump_3")
//    private Integer slump3;
//
//    @Column(name = "slump_4")
//    private Integer slump4;
//
//    @Column(name = "slump_5")
//    private Integer slump5;
//
//    @Column(name = "truck_id_1")
//    private Integer truckId1;
//
//    @Column(name = "truck_id_2")
//    private Integer truckId2;
//
//    @Column(name = "truck_id_3")
//    private Integer truckId3;
//
//    @Column(name = "truck_id_4")
//    private Integer truckId4;
//
//    @Column(name = "truck_id_5")
//    private Integer truckId5;
//
//    @Column(name = "casted_1")
//    private BigDecimal casted1;
//
//    @Column(name = "casted_2")
//    private BigDecimal casted2;
//
//    @Column(name = "casted_3")
//    private BigDecimal casted3;
//
//    @Column(name = "casted_4")
//    private BigDecimal casted4;
//
//    @Column(name = "casted_5")
//    private BigDecimal casted5;
//
//    @Column(name = "concreting_date_start")
//    private LocalDate concretingDateStart;
//
//    @Column(name = "concreting_start_time")
//    private LocalDate concretingStartTime;
//
//    @Column(name = "concreting_end_time")
//    private LocalDate concretingEndTime;
//
//    @Column(name = "concreting_order_time_1")
//    private LocalDate concretingOrderTime1;
//
//    @Column(name = "concreting_arrival_time_1")
//    private LocalDate concretingArrivalTime1;
//
//    @Column(name = "concreting_order_time_2")
//    private LocalDate concretingOrderTime2;
//
//    @Column(name = "concreting_arrival_time_2")
//    private LocalDate concretingArrivalTime2;
//
//    @Column(name = "concreting_order_time_3")
//    private LocalDate concretingOrderTime3;
//
//    @Column(name = "concreting_arrival_time_3")
//    private LocalDate concretingArrivalTime3;
//
//    @Column(name = "concreting_order_time_4")
//    private LocalDate concretingOrderTime4;
//
//    @Column(name = "concreting_arrival_time_4")
//    private LocalDate concretingArrivalTime4;
//
//    @Column(name = "concreting_order_time_5")
//    private LocalDate concretingOrderTime5;
//
//    @Column(name = "concreting_arrival_time_5")
//    private LocalDate concretingArrivalTime5;
//
//    @Column(name = "calculated_cumulative_cls")
//    private String calculatedCumulativeCls;
//
//    @Column(name = "calculated_theoric_cls")
//    private String calculatedTheoricCls;
//
//    @Column(name = "calculated_difference")
//    private String calculatedDifference;
//
//    @Column(name = "calculated_procent")
//    private String calculatedProcent;
//
//    @Column(name = "concrete_sent_back")
//    private BigDecimal concreteSentBack;
//
//    @ManyToOne
//    @JoinColumn(name = "login")
//    private User user;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
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
//    public Integer getSlump1() {
//        return slump1;
//    }
//
//    public void setSlump1(Integer slump1) {
//        this.slump1 = slump1;
//    }
//
//    public Integer getSlump2() {
//        return slump2;
//    }
//
//    public void setSlump2(Integer slump2) {
//        this.slump2 = slump2;
//    }
//
//    public Integer getSlump3() {
//        return slump3;
//    }
//
//    public void setSlump3(Integer slump3) {
//        this.slump3 = slump3;
//    }
//
//    public Integer getSlump4() {
//        return slump4;
//    }
//
//    public void setSlump4(Integer slump4) {
//        this.slump4 = slump4;
//    }
//
//    public Integer getSlump5() {
//        return slump5;
//    }
//
//    public void setSlump5(Integer slump5) {
//        this.slump5 = slump5;
//    }
//
//    public Integer getTruckId1() {
//        return truckId1;
//    }
//
//    public void setTruckId1(Integer truckId1) {
//        this.truckId1 = truckId1;
//    }
//
//    public Integer getTruckId2() {
//        return truckId2;
//    }
//
//    public void setTruckId2(Integer truckId2) {
//        this.truckId2 = truckId2;
//    }
//
//    public Integer getTruckId3() {
//        return truckId3;
//    }
//
//    public void setTruckId3(Integer truckId3) {
//        this.truckId3 = truckId3;
//    }
//
//    public Integer getTruckId4() {
//        return truckId4;
//    }
//
//    public void setTruckId4(Integer truckId4) {
//        this.truckId4 = truckId4;
//    }
//
//    public Integer getTruckId5() {
//        return truckId5;
//    }
//
//    public void setTruckId5(Integer truckId5) {
//        this.truckId5 = truckId5;
//    }
//
//    public BigDecimal getCasted1() {
//        return casted1;
//    }
//
//    public void setCasted1(BigDecimal casted1) {
//        this.casted1 = casted1;
//    }
//
//    public BigDecimal getCasted2() {
//        return casted2;
//    }
//
//    public void setCasted2(BigDecimal casted2) {
//        this.casted2 = casted2;
//    }
//
//    public BigDecimal getCasted3() {
//        return casted3;
//    }
//
//    public void setCasted3(BigDecimal casted3) {
//        this.casted3 = casted3;
//    }
//
//    public BigDecimal getCasted4() {
//        return casted4;
//    }
//
//    public void setCasted4(BigDecimal casted4) {
//        this.casted4 = casted4;
//    }
//
//    public BigDecimal getCasted5() {
//        return casted5;
//    }
//
//    public void setCasted5(BigDecimal casted5) {
//        this.casted5 = casted5;
//    }
//
//    public LocalDate getConcretingDateStart() {
//        return concretingDateStart;
//    }
//
//    public void setConcretingDateStart(LocalDate concretingDateStart) {
//        this.concretingDateStart = concretingDateStart;
//    }
//
//    public LocalDate getConcretingStartTime() {
//        return concretingStartTime;
//    }
//
//    public void setConcretingStartTime(LocalDate concretingStartTime) {
//        this.concretingStartTime = concretingStartTime;
//    }
//
//    public LocalDate getConcretingEndTime() {
//        return concretingEndTime;
//    }
//
//    public void setConcretingEndTime(LocalDate concretingEndTime) {
//        this.concretingEndTime = concretingEndTime;
//    }
//
//    public LocalDate getConcretingOrderTime1() {
//        return concretingOrderTime1;
//    }
//
//    public void setConcretingOrderTime1(LocalDate concretingOrderTime1) {
//        this.concretingOrderTime1 = concretingOrderTime1;
//    }
//
//    public LocalDate getConcretingArrivalTime1() {
//        return concretingArrivalTime1;
//    }
//
//    public void setConcretingArrivalTime1(LocalDate concretingArrivalTime1) {
//        this.concretingArrivalTime1 = concretingArrivalTime1;
//    }
//
//    public LocalDate getConcretingOrderTime2() {
//        return concretingOrderTime2;
//    }
//
//    public void setConcretingOrderTime2(LocalDate concretingOrderTime2) {
//        this.concretingOrderTime2 = concretingOrderTime2;
//    }
//
//    public LocalDate getConcretingArrivalTime2() {
//        return concretingArrivalTime2;
//    }
//
//    public void setConcretingArrivalTime2(LocalDate concretingArrivalTime2) {
//        this.concretingArrivalTime2 = concretingArrivalTime2;
//    }
//
//    public LocalDate getConcretingOrderTime3() {
//        return concretingOrderTime3;
//    }
//
//    public void setConcretingOrderTime3(LocalDate concretingOrderTime3) {
//        this.concretingOrderTime3 = concretingOrderTime3;
//    }
//
//    public LocalDate getConcretingArrivalTime3() {
//        return concretingArrivalTime3;
//    }
//
//    public void setConcretingArrivalTime3(LocalDate concretingArrivalTime3) {
//        this.concretingArrivalTime3 = concretingArrivalTime3;
//    }
//
//    public LocalDate getConcretingOrderTime4() {
//        return concretingOrderTime4;
//    }
//
//    public void setConcretingOrderTime4(LocalDate concretingOrderTime4) {
//        this.concretingOrderTime4 = concretingOrderTime4;
//    }
//
//    public LocalDate getConcretingArrivalTime4() {
//        return concretingArrivalTime4;
//    }
//
//    public void setConcretingArrivalTime4(LocalDate concretingArrivalTime4) {
//        this.concretingArrivalTime4 = concretingArrivalTime4;
//    }
//
//    public LocalDate getConcretingOrderTime5() {
//        return concretingOrderTime5;
//    }
//
//    public void setConcretingOrderTime5(LocalDate concretingOrderTime5) {
//        this.concretingOrderTime5 = concretingOrderTime5;
//    }
//
//    public LocalDate getConcretingArrivalTime5() {
//        return concretingArrivalTime5;
//    }
//
//    public void setConcretingArrivalTime5(LocalDate concretingArrivalTime5) {
//        this.concretingArrivalTime5 = concretingArrivalTime5;
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
//    public String getCalculatedTheoricCls() {
//        return calculatedTheoricCls;
//    }
//
//    public void setCalculatedTheoricCls(String calculatedTheoricCls) {
//        this.calculatedTheoricCls = calculatedTheoricCls;
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
//    public String getCalculatedProcent() {
//        return calculatedProcent;
//    }
//
//    public void setCalculatedProcent(String calculatedProcent) {
//        this.calculatedProcent = calculatedProcent;
//    }
//
//    public BigDecimal getConcreteSentBack() {
//        return concreteSentBack;
//    }
//
//    public void setConcreteSentBack(BigDecimal concreteSentBack) {
//        this.concreteSentBack = concreteSentBack;
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
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        PileConcreting pileConcreting = (PileConcreting) o;
//        if (pileConcreting.id == null || id == null) {
//            return false;
//        }
//        return Objects.equals(id, pileConcreting.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(id);
//    }
//
//    @Override
//    public String toString() {
//        return "PileConcreting{" +
//            "id=" + id +
//            ", mixDesign='" + mixDesign + "'" +
//            ", slump1='" + slump1 + "'" +
//            ", slump2='" + slump2 + "'" +
//            ", slump3='" + slump3 + "'" +
//            ", slump4='" + slump4 + "'" +
//            ", slump5='" + slump5 + "'" +
//            ", truckId1='" + truckId1 + "'" +
//            ", truckId2='" + truckId2 + "'" +
//            ", truckId3='" + truckId3 + "'" +
//            ", truckId4='" + truckId4 + "'" +
//            ", truckId5='" + truckId5 + "'" +
//            ", casted1='" + casted1 + "'" +
//            ", casted2='" + casted2 + "'" +
//            ", casted3='" + casted3 + "'" +
//            ", casted4='" + casted4 + "'" +
//            ", casted5='" + casted5 + "'" +
//            ", concretingDateStart='" + concretingDateStart + "'" +
//            ", concretingStartTime='" + concretingStartTime + "'" +
//            ", concretingEndTime='" + concretingEndTime + "'" +
//            ", concretingOrderTime1='" + concretingOrderTime1 + "'" +
//            ", concretingArrivalTime1='" + concretingArrivalTime1 + "'" +
//            ", concretingOrderTime2='" + concretingOrderTime2 + "'" +
//            ", concretingArrivalTime2='" + concretingArrivalTime2 + "'" +
//            ", concretingOrderTime3='" + concretingOrderTime3 + "'" +
//            ", concretingArrivalTime3='" + concretingArrivalTime3 + "'" +
//            ", concretingOrderTime4='" + concretingOrderTime4 + "'" +
//            ", concretingArrivalTime4='" + concretingArrivalTime4 + "'" +
//            ", concretingOrderTime5='" + concretingOrderTime5 + "'" +
//            ", concretingArrivalTime5='" + concretingArrivalTime5 + "'" +
//            ", calculatedCumulativeCls='" + calculatedCumulativeCls + "'" +
//            ", calculatedTheoricCls='" + calculatedTheoricCls + "'" +
//            ", calculatedDifference='" + calculatedDifference + "'" +
//            ", calculatedProcent='" + calculatedProcent + "'" +
//            ", concreteSentBack='" + concreteSentBack + "'" +
//            '}';
//    }
}
