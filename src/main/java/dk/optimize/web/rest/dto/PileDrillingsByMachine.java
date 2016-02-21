package dk.optimize.web.rest.dto;


import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

/**
 * Date: 20/02/16
 */
public class PileDrillingsByMachine {
    private String machine;
    private BigDecimal drillings;

    public PileDrillingsByMachine(BigDecimal drillings, String machine) {
        this.drillings = drillings;
        this.machine = machine;
    }

    public BigDecimal getDrillings() {
        return drillings;
    }

    public void setDrillings(BigDecimal drillings) {
        this.drillings = drillings;
    }

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    @Override
    public String toString() {
        return "PileDrillingsPerWeek{" +
            "drillings=" + drillings +
            ", machine=" + machine +
            '}';
    }
}
