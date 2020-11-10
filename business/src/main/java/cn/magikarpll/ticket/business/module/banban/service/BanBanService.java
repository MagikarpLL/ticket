package cn.magikarpll.ticket.business.module.banban.service;

import cn.magikarpll.ticket.business.module.banban.entity.response.*;

public interface BanBanService {

    void change();

    AreaEntity area();

    DeptEntity dept();

    AppointmentCountEntity getAppointmentCount();

    SimulatorNumberEntity getSimulatorNumber();

    Boolean saveAppointment();

}
