package dk.optimize.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProjectInfo.
 */
@Entity
@Table(name = "project_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "projectinfo")
public class ProjectInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "wir_code")
    private String wirCode;

    @Column(name = "phase")
    private String phase;

    @Column(name = "site")
    private String site;

    @Column(name = "sub_contractor")
    private String subContractor;

    @Column(name = "record_sn")
    private Integer recordSN;

    @Column(name = "contract_nr")
    private String contractNr;

    @ManyToMany(mappedBy = "projects")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "projectInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pile> piles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWirCode() {
        return wirCode;
    }

    public void setWirCode(String wirCode) {
        this.wirCode = wirCode;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSubContractor() {
        return subContractor;
    }

    public void setSubContractor(String subContractor) {
        this.subContractor = subContractor;
    }

    public Integer getRecordSN() {
        return recordSN;
    }

    public void setRecordSN(Integer recordSN) {
        this.recordSN = recordSN;
    }

    public String getContractNr() {
        return contractNr;
    }

    public void setContractNr(String contractNr) {
        this.contractNr = contractNr;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Pile> getPiles() {
        return piles;
    }

    public void setPiles(Set<Pile> piles) {
        this.piles = piles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectInfo projectInfo = (ProjectInfo) o;
        if(projectInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, projectInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", wirCode='" + wirCode + "'" +
            ", phase='" + phase + "'" +
            ", site='" + site + "'" +
            ", subContractor='" + subContractor + "'" +
            ", recordSN='" + recordSN + "'" +
            ", contractNr='" + contractNr + "'" +
            '}';
    }
}
