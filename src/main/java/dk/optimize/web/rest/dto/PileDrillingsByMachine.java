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
    public final String totalDrillTime;
    public String machine;
    public BigDecimal totalDrillingDepth;
    public long totalDrillingMinutes;
    public BigDecimal meterDrillingPerHour;
    public List<PileDrilling> drillings;
    public Map<Long, Long> drillingMinutesMap = new HashMap<>();

    public PileDrillingsByMachine(BigDecimal totalDrillingDepth, String machine, long meterDrillMin, BigDecimal meterDrillingPerHour, String totalDrillTime, List<PileDrilling> pileDrillings, Map<Long, Long> drillingMinutesMap) {
        this.totalDrillingDepth = totalDrillingDepth;
        this.machine = machine;
        this.totalDrillingMinutes = meterDrillMin;
        this.totalDrillTime = totalDrillTime;
        this.meterDrillingPerHour = meterDrillingPerHour;
        this.drillings = pileDrillings;
        this.drillingMinutesMap = drillingMinutesMap;
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
