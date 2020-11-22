package cn.magikarpll.ticket.business.module.banban.service;

import cn.magikarpll.ticket.business.module.banban.entity.request.SaveAppointmentRequest;
import cn.magikarpll.ticket.business.module.banban.entity.request.SimulatorNumberRequest;
import cn.magikarpll.ticket.business.module.banban.entity.response.*;
import cn.magikarpll.ticket.common.exception.BusinessException;

import java.util.List;

public interface BanBanService {

    List<ChangeEntity> change() throws BusinessException, Exception;

    AreaEntity area() throws BusinessException, Exception;

    List<DeptEntity> dept(Integer areaId) throws Exception;

    List<DeptEntity> deptBatch(List<Integer> areaIds) throws Exception;

    List<AppointmentCountEntity> getAppointmentCount(Integer roomId, String planDate) throws Exception;

    List<AppointmentCountEntity> getAppointmentCountBatch(List<Integer> roomIds, List<String> planDates) throws Exception;

    List<SimulatorNumberEntity> getSimulatorNumber(Integer roomId, String planDate, String time) throws Exception;

    List<SimulatorNumberEntity> getSimulatorNumberBatch(List<SimulatorNumberRequest> simulatorNumberRequests) throws Exception;

    Boolean saveAppointment(SaveAppointmentRequest appointmentRequest) throws BusinessException, Exception;

}
