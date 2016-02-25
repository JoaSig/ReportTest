package dk.optimize.domain.report;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Date: 24/02/16
 */
//@Entity
//@Table(name = "general_info")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "generalinfo")
public class GeneralInfoOld {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "trevi_ref_nr")
//    private String treviRefNr;
//
//    @Column(name = "piling_rig_type")
//    private String pilingRigType;
//
//    @Column(name = "machine_serial_nr")
//    private Integer machineSerialNr;
//
//    @Column(name = "date")
//    private LocalDate date;
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
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
//    public Integer getMachineSerialNr() {
//        return machineSerialNr;
//    }
//
//    public void setMachineSerialNr(Integer machineSerialNr) {
//        this.machineSerialNr = machineSerialNr;
//    }
//
//    public String getPilingRigType() {
//        return pilingRigType;
//    }
//
//    public void setPilingRigType(String pilingRigType) {
//        this.pilingRigType = pilingRigType;
//    }
//
//    public String getTreviRefNr() {
//        return treviRefNr;
//    }
//
//    public void setTreviRefNr(String treviRefNr) {
//        this.treviRefNr = treviRefNr;
//    }
}
