package dk.optimize.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GeneralInfo.
 */
@Entity
@Table(name = "general_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "generalinfo")
public class GeneralInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "trevi_ref_nr")
    private String treviRefNr;
    
    @Column(name = "piling_rig_type")
    private String pilingRigType;
    
    @Column(name = "machine_serial_nr")
    private Integer machineSerialNr;
    
    @Column(name = "date")
    private LocalDate date;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTreviRefNr() {
        return treviRefNr;
    }
    
    public void setTreviRefNr(String treviRefNr) {
        this.treviRefNr = treviRefNr;
    }

    public String getPilingRigType() {
        return pilingRigType;
    }
    
    public void setPilingRigType(String pilingRigType) {
        this.pilingRigType = pilingRigType;
    }

    public Integer getMachineSerialNr() {
        return machineSerialNr;
    }
    
    public void setMachineSerialNr(Integer machineSerialNr) {
        this.machineSerialNr = machineSerialNr;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneralInfo generalInfo = (GeneralInfo) o;
        if(generalInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, generalInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GeneralInfo{" +
            "id=" + id +
            ", treviRefNr='" + treviRefNr + "'" +
            ", pilingRigType='" + pilingRigType + "'" +
            ", machineSerialNr='" + machineSerialNr + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
