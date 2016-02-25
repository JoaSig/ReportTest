package dk.optimize.domain.report;

import dk.optimize.domain.User;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Date: 24/02/16
 */
//@Entity
//@Table(name = "signature")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "signature")
public class SignatureOld {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "owner_id")
//    private User owner;
//
//    @Column(name = "signed_at")
//    private LocalDate signedAt;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }
//
//    public LocalDate getSignedAt() {
//        return signedAt;
//    }
//
//    public void setSignedAt(LocalDate signedAt) {
//        this.signedAt = signedAt;
//    }
}
