package cn.magikarpll.ticket.business.module.banban.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dept实体for http://sim.ctl.chelizitech.com:9901/api/simulator/dept
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptEntity {
    private Integer RoomId;

    private Integer Areaid;

    private String DeptName;

    private String Address;

    private String Phone;

    private String Areaname;

    private String City;

    private String ImageUrl;

    private String Lng;

    private String Lat;

    private String Distance;

}
