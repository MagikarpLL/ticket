package cn.magikarpll.ticket.business.module.banban.service;

import cn.magikarpll.ticket.business.module.banban.entity.response.*;
import cn.magikarpll.ticket.common.exception.BusinessException;

public interface BanBanService {

    void change() throws BusinessException;

    AreaEntity area() throws BusinessException;

    DeptEntity dept() throws BusinessException;

    AppointmentCountEntity getAppointmentCount() throws BusinessException;

    SimulatorNumberEntity getSimulatorNumber() throws BusinessException;

    Boolean saveAppointment() throws BusinessException;

}
