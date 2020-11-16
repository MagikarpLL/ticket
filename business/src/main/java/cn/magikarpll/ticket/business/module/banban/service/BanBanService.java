package cn.magikarpll.ticket.business.module.banban.service;

import cn.magikarpll.ticket.business.module.banban.entity.response.*;
import cn.magikarpll.ticket.common.exception.BusinessException;

import java.util.List;

public interface BanBanService {

    void change() throws BusinessException, Exception;

    AreaEntity area() throws BusinessException, Exception;

    List<DeptEntity> dept(Integer areaId) throws BusinessException, Exception;

    List<AppointmentCountEntity> getAppointmentCount(Integer roomId, String planDate) throws BusinessException, Exception;

    List<SimulatorNumberEntity> getSimulatorNumber(Integer roomId, String planDate, String time) throws BusinessException, Exception;

    Boolean saveAppointment() throws BusinessException, Exception;

}
