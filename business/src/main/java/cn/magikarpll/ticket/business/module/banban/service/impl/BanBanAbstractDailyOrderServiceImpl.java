package cn.magikarpll.ticket.business.module.banban.service.impl;

import cn.magikarpll.ticket.business.module.banban.constant.BanBanConstant;
import cn.magikarpll.ticket.business.module.banban.entity.request.AppointmentCountRequest;
import cn.magikarpll.ticket.business.module.banban.entity.request.SaveAppointmentRequest;
import cn.magikarpll.ticket.business.module.banban.entity.request.SimulatorNumberRequest;
import cn.magikarpll.ticket.business.module.banban.entity.response.AppointmentCountEntity;
import cn.magikarpll.ticket.business.module.banban.entity.response.SimulatorNumberEntity;
import cn.magikarpll.ticket.business.module.banban.service.BanBanService;
import cn.magikarpll.ticket.common.utils.StreamUtils;
import cn.magikarpll.ticket.order.async.service.impl.AbstractDailyOrderServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableAsync
public class BanBanAbstractDailyOrderServiceImpl extends AbstractDailyOrderServiceImpl {

    @Resource
    private BanBanService banBanService;

    @Override
    @Async(value = "scheduledThreadPoolExecutor")
    public void dailyOrder() throws Exception {
        List<Integer> targetRoomList = StreamUtils.readObjectForList(new File("sortedRoomId"));
        List<SimulatorNumberRequest> simulatorNumberRequests = new ArrayList<>();
        List<SimulatorNumberRequest> tempSimulatorNumberRequests = null;
        List<String> orderDates = computeDate();
        //查询指定训练场的可用时间段是否可以预约
        for (Integer iR : targetRoomList) {
            for(String orderDate: orderDates){
                List<AppointmentCountEntity> appointmentCountEntities = filterAppointmentCount(banBanService.getAppointmentCount(iR, orderDate));
                simulatorNumberRequests = SimulatorNumberRequest.convertToEntiy(appointmentCountEntities, iR, orderDate);
                if (null != tempSimulatorNumberRequests) {
                    simulatorNumberRequests.addAll(tempSimulatorNumberRequests);
                    tempSimulatorNumberRequests = null;
                }
            }
        }
        //若指定训练场的可用时间段均无法预约，则结束
        if(simulatorNumberRequests.isEmpty()){
            return;
        }
        //查询可用时间段的可用模拟机编号并进行筛选
        List<SaveAppointmentRequest> saveAppointmentRequests = new ArrayList<>();
        for(SimulatorNumberRequest simulatorNumberRequest: simulatorNumberRequests){
            //查询 并 筛选出已备案的模拟机编号
            List<SimulatorNumberEntity> tempSimulatorNumberEntityList = banBanService.getSimulatorNumber(simulatorNumberRequest.getRoomId(),simulatorNumberRequest.getPlanDate()
                    , simulatorNumberRequest.getTime());
            if(null != tempSimulatorNumberEntityList){
                tempSimulatorNumberEntityList =  tempSimulatorNumberEntityList.stream().filter(s -> BanBanConstant.SIMULATOR_NUMBER_SET.contains(s.getName())).collect(Collectors.toList());
            }
            if(null != tempSimulatorNumberEntityList){
                saveAppointmentRequests.addAll(SaveAppointmentRequest.convertToEntiy(tempSimulatorNumberEntityList, simulatorNumberRequest));
            }
        }
        //若指定时间的无可用模拟机，则结束
        if(saveAppointmentRequests.isEmpty()){
            return;
        }
        //调用预定接口
        Boolean result = false;
        for(SaveAppointmentRequest saveAppointmentRequest: saveAppointmentRequests){
            result = banBanService.saveAppointment(saveAppointmentRequest);
            if(result = true){
                //记录已经预约的时间点和地点

                //判断是否需要继续抢
            }
        }
    }

    /**
     *计算出抢票查询的日期
     * @return
     */
    private List<String> computeDate(){

        return null;
    }

    private List<AppointmentCountEntity> filterAppointmentCount(List<AppointmentCountEntity> appointmentCountEntities){

        return null;
    }


}
