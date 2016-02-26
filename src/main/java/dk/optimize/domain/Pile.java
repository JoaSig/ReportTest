package dk.optimize.domain;

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

    @Column(name = "created_at")
    private LocalDate createdAt;
    
    @Column(name = "last_updated_at")
    private LocalDate lastUpdatedAt;
    
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    
    @Column(name = "next_pile")
    private Long nextPile;
    
    @Column(name = "prev_pile")
    private Long prevPile;
    
    @Column(name = "number")
    private Long number;
    
    @Column(name = "comment")
    private String comment;
    
    @OneToOne
    private User user;

    @OneToOne
    private Concreting concreting;

    @OneToOne
    private Drilling drilling;

    @OneToOne
    private SteelCage steelCage;

    @ManyToOne
    @JoinColumn(name = "project_info_id")
    private ProjectInfo projectInfo;

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

    public Long getNextPile() {
        return nextPile;
    }
    
    public void setNextPile(Long nextPile) {
        this.nextPile = nextPile;
    }

    public Long getPrevPile() {
        return prevPile;
    }
    
    public void setPrevPile(Long prevPile) {
        this.prevPile = prevPile;
    }

    public Long getNumber() {
        return number;
    }
    
    public void setNumber(Long number) {
        this.number = number;
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

    public Concreting getConcreting() {
        return concreting;
    }

    public void setConcreting(Concreting concreting) {
        this.concreting = concreting;
    }

    public Drilling getDrilling() {
        return drilling;
    }

    public void setDrilling(Drilling drilling) {
        this.drilling = drilling;
    }

    public SteelCage getSteelCage() {
        return steelCage;
    }

    public void setSteelCage(SteelCage steelCage) {
        this.steelCage = steelCage;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
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
            ", createdAt='" + createdAt + "'" +
            ", lastUpdatedAt='" + lastUpdatedAt + "'" +
            ", lastUpdatedBy='" + lastUpdatedBy + "'" +
            ", nextPile='" + nextPile + "'" +
            ", prevPile='" + prevPile + "'" +
            ", number='" + number + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
