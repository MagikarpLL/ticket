package cn.magikarpll.ticket.business.module.banban.service;

import cn.magikarpll.ticket.business.module.banban.entity.response.*;
import cn.magikarpll.ticket.common.exception.BusinessException;

import java.util.List;

public interface BanBanService {

    void change() throws BusinessException;

    AreaEntity area() throws BusinessException;

    List<DeptEntity> dept(Integer areaId) throws BusinessException;

    List<AppointmentCountEntity> getAppointmentCount(Integer roomId, String planDate) throws BusinessException;

    List<SimulatorNumberEntity> getSimulatorNumber(Integer roomId, String planDate, String time) throws BusinessException;

    Boolean saveAppointment() throws BusinessException;

}
