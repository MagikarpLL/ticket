package cn.magikarpll.ticket.business.module.banban.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 模拟机实体 for http://sim.ctl.chelizitech.com:9901/api/simulator/getSimulatorNumber
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulatorNumberEntity {
    private Integer SimId;

    private String name;

    private Long isOrder;

}
