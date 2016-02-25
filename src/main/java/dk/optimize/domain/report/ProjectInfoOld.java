package dk.optimize.domain.report;

import dk.optimize.domain.User;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 24/02/16
 */
//@Entity
//@Table(name = "project_info")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "projectinfo")
public class ProjectInfoOld {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "wir_code")
//    private String wirCode;
//
//    @Column(name = "phase")
//    private String phase;
//
//    @Column(name = "site")
//    private String site;
//
//    @Column(name = "sub_contractor")
//    private String subContractor;
//
//    @Column(name = "record_sn")
//    private Integer recordSN;
//
//    @Column(name = "contract_nr")
//    private String contractNr;
//
//    @ManyToMany(mappedBy="projects")
//    private Set<User> users = new HashSet<>();
//
//    @OneToMany(mappedBy = "projectInfo", cascade = CascadeType.ALL)
//    private Set<PileOld> piles = new HashSet<>();
//
//    public String getContractNr() {
//        return contractNr;
//    }
//
//    public void setContractNr(String contractNr) {
//        this.contractNr = contractNr;
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
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPhase() {
//        return phase;
//    }
//
//    public void setPhase(String phase) {
//        this.phase = phase;
//    }
//
//    public Set<PileOld> getPiles() {
//        return piles;
//    }
//
//    public void setPiles(Set<PileOld> piles) {
//        this.piles = piles;
//    }
//
//    public Integer getRecordSN() {
//        return recordSN;
//    }
//
//    public void setRecordSN(Integer recordSN) {
//        this.recordSN = recordSN;
//    }
//
//    public String getSite() {
//        return site;
//    }
//
//    public void setSite(String site) {
//        this.site = site;
//    }
//
//    public String getSubContractor() {
//        return subContractor;
//    }
//
//    public void setSubContractor(String subContractor) {
//        this.subContractor = subContractor;
//    }
//
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
//
//    public String getWirCode() {
//        return wirCode;
//    }
//
//    public void setWirCode(String wirCode) {
//        this.wirCode = wirCode;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof ProjectInfoOld)) return false;
//
//        ProjectInfoOld that = (ProjectInfoOld) o;
//
//        if (id != null ? !id.equals(that.id) : that.id != null) return false;
//        if (name != null ? !name.equals(that.name) : that.name != null) return false;
//        if (wirCode != null ? !wirCode.equals(that.wirCode) : that.wirCode != null) return false;
//        if (phase != null ? !phase.equals(that.phase) : that.phase != null) return false;
//        if (site != null ? !site.equals(that.site) : that.site != null) return false;
//        if (subContractor != null ? !subContractor.equals(that.subContractor) : that.subContractor != null) return false;
//        if (recordSN != null ? !recordSN.equals(that.recordSN) : that.recordSN != null) return false;
//        return !(contractNr != null ? !contractNr.equals(that.contractNr) : that.contractNr != null);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id != null ? id.hashCode() : 0;
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (wirCode != null ? wirCode.hashCode() : 0);
//        result = 31 * result + (phase != null ? phase.hashCode() : 0);
//        result = 31 * result + (site != null ? site.hashCode() : 0);
//        result = 31 * result + (subContractor != null ? subContractor.hashCode() : 0);
//        result = 31 * result + (recordSN != null ? recordSN.hashCode() : 0);
//        result = 31 * result + (contractNr != null ? contractNr.hashCode() : 0);
//        return result;
//    }
}
