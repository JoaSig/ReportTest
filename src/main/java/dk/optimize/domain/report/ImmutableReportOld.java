package dk.optimize.domain.report;

import dk.optimize.domain.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 24/02/16
 */
//@Entity
//@Table(name = "immutable_report")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "immutablereport")
public class ImmutableReportOld {

//    @PrePersist
//    public void onCreate() {
//        if (getCreatedAt() == null)
//            setCreatedAt(LocalDate.now());
//    }
//
//    @PreUpdate
//    public void onUpdate() throws Exception {
//        throw new Exception("The report is immutable and can therefore not be updated");
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
//    @Column(name = "send_at")
//    private LocalDate sendAt;
//
//    @Basic
//    @Column(name = "received_at")
//    private LocalDate receivedAt;
//
//    @OneToOne
//    @JoinColumn(name = "creator_id")
//    private User creator;
//
//    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
//    private Set<SignatureOld> signatures = new HashSet<>();
//
//    @OneToOne
//    @JoinColumn(name = "general_info_id")
//    private GeneralInfoOld generalInfo;
//
//    @OneToOne
//    @JoinColumn(name = "project_info_id")
//    private ProjectInfoOld projectInfo;
//
//    @Column(name = "description")
//    private String description;
//
//    @Column(name = "comment")
//    private String comment;
//
//    @Column(name = "emails")
//    private String emails;
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
//    public User getCreator() {
//        return creator;
//    }
//
//    public void setCreator(User creator) {
//        this.creator = creator;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getEmails() {
//        return emails;
//    }
//
//    public void setEmails(String emails) {
//        this.emails = emails;
//    }
//
//    public GeneralInfoOld getGeneralInfo() {
//        return generalInfo;
//    }
//
//    public void setGeneralInfo(GeneralInfoOld generalInfo) {
//        this.generalInfo = generalInfo;
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
//    public ProjectInfoOld getProjectInfo() {
//        return projectInfo;
//    }
//
//    public void setProjectInfo(ProjectInfoOld projectInfo) {
//        this.projectInfo = projectInfo;
//    }
//
//    public LocalDate getReceivedAt() {
//        return receivedAt;
//    }
//
//    public void setReceivedAt(LocalDate receivedAt) {
//        this.receivedAt = receivedAt;
//    }
//
//    public LocalDate getSendAt() {
//        return sendAt;
//    }
//
//    public void setSendAt(LocalDate sendAt) {
//        this.sendAt = sendAt;
//    }
//
//    public Set<SignatureOld> getSignatures() {
//        return signatures;
//    }
//
//    public void setSignatures(Set<SignatureOld> signatures) {
//        this.signatures = signatures;
//    }
}
