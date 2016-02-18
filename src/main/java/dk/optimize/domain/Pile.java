package dk.optimize.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pile.
 */
@Entity
@Table(name = "pile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pile")
public class Pile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mix_design")
    private String mixDesign;

    @Column(name = "slump_flow_test")
    private Integer slumpFlowTest;

    @Column(name = "pouring_rate", precision=10, scale=2)
    private BigDecimal pouringRate;

    @Column(name = "total_casted_volume", precision=10, scale=2)
    private BigDecimal totalCastedVolume;

    @Column(name = "theoretical_concrete_volume", precision=10, scale=2)
    private BigDecimal theoreticalConcreteVolume;

    @Column(name = "overconsumption_of_concrete")
    private Integer overconsumptionOfConcrete;

    @Column(name = "comment")
    private String comment;

    @Column(name = "signature_date")
    private ZonedDateTime signatureDate;

    @Column(name = "sub_contractor")
    private String subContractor;

    @Column(name = "main_contractor")
    private String mainContractor;

    @Column(name = "client")
    private String client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTheoreticalConcreteVolume() {
        return theoreticalConcreteVolume;
    }

    public void setTheoreticalConcreteVolume(BigDecimal theoreticalConcreteVolume) {
        this.theoreticalConcreteVolume = theoreticalConcreteVolume;
    }

    public Integer getOverconsumptionOfConcrete() {
        return overconsumptionOfConcrete;
    }

    public void setOverconsumptionOfConcrete(Integer overconsumptionOfConcrete) {
        this.overconsumptionOfConcrete = overconsumptionOfConcrete;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(ZonedDateTime signatureDate) {
        this.signatureDate = signatureDate;
    }

    public String getSubContractor() {
        return subContractor;
    }

    public void setSubContractor(String subContractor) {
        this.subContractor = subContractor;
    }

    public String getMainContractor() {
        return mainContractor;
    }

    public void setMainContractor(String mainContractor) {
        this.mainContractor = mainContractor;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pile pile = (Pile) o;
        if(pile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pile{" +
            "id=" + id +
            ", mixDesign='" + mixDesign + "'" +
            ", slumpFlowTest='" + slumpFlowTest + "'" +
            ", pouringRate='" + pouringRate + "'" +
            ", totalCastedVolume='" + totalCastedVolume + "'" +
            ", theoreticalConcreteVolume='" + theoreticalConcreteVolume + "'" +
            ", overconsumptionOfConcrete='" + overconsumptionOfConcrete + "'" +
            ", comment='" + comment + "'" +
            ", signatureDate='" + signatureDate + "'" +
            ", subContractor='" + subContractor + "'" +
            ", mainContractor='" + mainContractor + "'" +
            ", client='" + client + "'" +
            '}';
    }
}
