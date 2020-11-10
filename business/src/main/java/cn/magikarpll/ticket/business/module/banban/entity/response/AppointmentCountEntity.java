package cn.magikarpll.ticket.business.module.banban.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体for http://sim.ctl.chelizitech.com:9901/api/simulator/GetAppointmentCount
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCountEntity {

    private String Times;

    private Integer HadOrder;

    private Integer CanOrder;

    private String State;

}
