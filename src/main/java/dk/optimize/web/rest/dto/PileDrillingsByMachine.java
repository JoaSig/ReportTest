package dk.optimize.web.rest.dto;


import dk.optimize.domain.PileDrilling;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 20/02/16
 */
public class PileDrillingsByMachine {
    private final String totalDrillTime;
    private String machine;
    private BigDecimal totalDrillingDepth;
    private long totalDrillingMinutes;
    private BigDecimal meterDrillingPerHour;
    private List<PileDrilling> drillings;
    private Map<Long, Long> drillingMinutesMap = new HashMap<>();

    public PileDrillingsByMachine(BigDecimal totalDrillingDepth, String machine, long meterDrillPerHr, BigDecimal meterDrillingPerHour, String totalDrillTime, List<PileDrilling> pileDrillings, Map<Long, Long> drillingMinutesMap) {
        this.totalDrillingDepth = totalDrillingDepth;
        this.machine = machine;
        this.totalDrillingMinutes = meterDrillPerHr;
        this.totalDrillTime = totalDrillTime;
        this.meterDrillingPerHour = meterDrillingPerHour;
        this.drillings = pileDrillings;
        this.drillingMinutesMap = drillingMinutesMap;
    }

    public BigDecimal getTotalDrillingDepth() {
        return totalDrillingDepth;
    }

    public long getTotalDrillingMinutes() {
        return totalDrillingMinutes;
    }

    public void setTotalDrillingMinutes(long totalDrillingMinutes) {
        this.totalDrillingMinutes = totalDrillingMinutes;
    }

    public void setTotalDrillingDepth(BigDecimal totalDrillingDepth) {
        this.totalDrillingDepth = totalDrillingDepth;
    }

    public String getTotalDrillTime() {
        return totalDrillTime;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public BigDecimal getMeterDrillingPerHour() {
        return meterDrillingPerHour;
    }

    public void setMeterDrillingPerHour(BigDecimal meterDrillingPerHour) {
        this.meterDrillingPerHour = meterDrillingPerHour;
    }

    public List<PileDrilling> getDrillings() {
        return drillings;
    }

    public void setDrillings(List<PileDrilling> drillings) {
        this.drillings = drillings;
    }

    public Map<Long, Long> getDrillingMinutesMap() {
        return drillingMinutesMap;
    }

    public void setDrillingMinutesMap(Map<Long, Long> drillingMinutesMap) {
        this.drillingMinutesMap = drillingMinutesMap;
        this.drillingMinutesMap = drillingMinutesMap;
    }

    @Override
    public String toString() {
        return "PileDrillingsByMachine{" +
            "totalDrillingDepth=" + totalDrillingDepth + "m" +
            ", totalDrillTime='" + totalDrillTime + '\'' +
            ", machine='" + machine + '\'' +
            ", totalDrillingMinutes=" + totalDrillingMinutes + "min" +
            ", meterDrillingPerHour=" + meterDrillingPerHour + "m/h" +
            '}';
    }
}
