package cn.magikarpll.ticket.business.module.banban.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地区实体for  http://sim.ctl.chelizitech.com:9901/api/simulator/area
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaEntity {

    private String areaOrganID;

    private String areaOragnName;

}
