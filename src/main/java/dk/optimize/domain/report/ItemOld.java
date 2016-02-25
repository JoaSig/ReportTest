package dk.optimize.domain.report;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Date: 24/02/16
 */
//@Entity
//@Table(name = "item")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "item")
public class ItemOld implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "order_time")
//    private Timestamp orderTime;
//
//    @Column(name = "arrival_time")
//    private Timestamp arrivalTime;
//
//    @Column(name = "truck")
//    private Integer truck;
//
//    @Column(name = "casted", precision = 10, scale = 2)
//    private BigDecimal casted;
//
//    @Column(name = "slump")
//    private Integer slump;
//
//    @Column(name = "theoretical_concrete_volume", precision = 10, scale = 2)
//    private BigDecimal theoreticalConcreteVolume;
//
//    @ManyToOne
//    @JoinColumn(name = "concreting_id")
//    private ConcretingOld concreting;
//
//    public Timestamp getArrivalTime() {
//        return arrivalTime;
//    }
//
//    public void setArrivalTime(Timestamp arrivalTime) {
//        this.arrivalTime = arrivalTime;
//    }
//
//    public BigDecimal getCasted() {
//        return casted;
//    }
//
//    public void setCasted(BigDecimal casted) {
//        this.casted = casted;
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
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Timestamp getOrderTime() {
//        return orderTime;
//    }
//
//    public void setOrderTime(Timestamp orderTime) {
//        this.orderTime = orderTime;
//    }
//
//    public Integer getSlump() {
//        return slump;
//    }
//
//    public void setSlump(Integer slump) {
//        this.slump = slump;
//    }
//
//    public BigDecimal getTheoreticalConcreteVolume() {
//        return theoreticalConcreteVolume;
//    }
//
//    public Integer getTruck() {
//        return truck;
//    }
//
//    public void setTruck(Integer truck) {
//        this.truck = truck;
//    }
//
//    public void setTheoreticalConcreteVolume(BigDecimal theoreticalConcreteVolume) {
//        this.theoreticalConcreteVolume = theoreticalConcreteVolume;
//    }


}
