package dk.optimize.domain;

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
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_time")
    private LocalDate orderTime;
    
    @Column(name = "arrival_time")
    private LocalDate arrivalTime;
    
    @Column(name = "truck")
    private Integer truck;
    
    @Column(name = "casted", precision=10, scale=2)
    private BigDecimal casted;
    
    @Column(name = "slump")
    private Integer slump;
    
    @Column(name = "theoretical_concrete_volume", precision=10, scale=2)
    private BigDecimal theoreticalConcreteVolume;
    
    @ManyToOne
    @JoinColumn(name = "concreting_id")
    private Concreting concreting;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderTime() {
        return orderTime;
    }
    
    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDate getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(LocalDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getTruck() {
        return truck;
    }
    
    public void setTruck(Integer truck) {
        this.truck = truck;
    }

    public BigDecimal getCasted() {
        return casted;
    }
    
    public void setCasted(BigDecimal casted) {
        this.casted = casted;
    }

    public Integer getSlump() {
        return slump;
    }
    
    public void setSlump(Integer slump) {
        this.slump = slump;
    }

    public BigDecimal getTheoreticalConcreteVolume() {
        return theoreticalConcreteVolume;
    }
    
    public void setTheoreticalConcreteVolume(BigDecimal theoreticalConcreteVolume) {
        this.theoreticalConcreteVolume = theoreticalConcreteVolume;
    }

    public Concreting getConcreting() {
        return concreting;
    }

    public void setConcreting(Concreting concreting) {
        this.concreting = concreting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        if(item.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", orderTime='" + orderTime + "'" +
            ", arrivalTime='" + arrivalTime + "'" +
            ", truck='" + truck + "'" +
            ", casted='" + casted + "'" +
            ", slump='" + slump + "'" +
            ", theoreticalConcreteVolume='" + theoreticalConcreteVolume + "'" +
            '}';
    }
}
