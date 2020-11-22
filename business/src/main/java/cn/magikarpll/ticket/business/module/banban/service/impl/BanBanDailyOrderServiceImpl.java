package cn.magikarpll.ticket.business.module.banban.service.impl;

import cn.magikarpll.ticket.business.module.banban.constant.BanBanConstant;
import cn.magikarpll.ticket.business.module.banban.entity.request.SaveAppointmentRequest;
import cn.magikarpll.ticket.business.module.banban.entity.request.SimulatorNumberRequest;
import cn.magikarpll.ticket.business.module.banban.entity.response.AppointmentCountEntity;
import cn.magikarpll.ticket.business.module.banban.entity.response.SimulatorNumberEntity;
import cn.magikarpll.ticket.business.module.banban.service.BanBanService;
import cn.magikarpll.ticket.common.constants.CommonConstants;
import cn.magikarpll.ticket.common.utils.DateUtils;
import cn.magikarpll.ticket.common.utils.StreamUtils;
import cn.magikarpll.ticket.order.async.service.impl.AbstractDailyOrderServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableAsync
public class BanBanDailyOrderServiceImpl extends AbstractDailyOrderServiceImpl {

//    @Value("${}")
    private String startTime;

//    @Value("${}")
    private String whiteListDate;

//    @Value("${}")
    private String whiteListTime;

//    @Value("${}")
    private String blackListDate;

//    @Value("${}")
    private String blackListTime;


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
                List<AppointmentCountEntity> appointmentCountEntities = filterAppointmentCounts(banBanService.getAppointmentCount(iR, orderDate), orderDate);
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
        String now = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
        List<String> result = new ArrayList<>();
        if(now.compareTo(startTime)<0){
            //如果当前时间早于开始时间
            result.add(DateUtils.getSpecifyDate(null));
            result.add(DateUtils.getSpecifyDate(1));
        }else{
            //如果当前时间晚于或等于当前时间
            result.add(DateUtils.getSpecifyDate(null));
            result.add(DateUtils.getSpecifyDate(1));
            result.add(DateUtils.getSpecifyDate(2));
        }
        return result;
    }

    private List<AppointmentCountEntity> filterAppointmentCounts(List<AppointmentCountEntity> appointmentCountEntities, String orderDate){
        List<AppointmentCountEntity> whiteList = appointmentCountEntities.stream().filter(a -> this.filterWhiteListTime(a, orderDate)).collect(Collectors.toList());
//        List<AppointmentCountEntity>  weekday = appointmentCountEntities.stream()
//                .filter(a -> a.getTim)
//                .filter(a -> !this.filterWhiteListTime(a, orderDate))
//                .filter(a -> this.filterBlackListTime(a, orderDate))
//                .collect(Collectors.toList());
//        List<AppointmentCountEntity> weekend = appointmentCountEntities.stream().filter()


        return null;
    }

    private boolean filterAppointmentCount(AppointmentCountEntity appointmentCountEntity, String orderDate){
        //如果是工作日,则限定为六点之后才行
        if(DateUtils.isWeekDay(orderDate)){
            if(startTime.compareTo(appointmentCountEntity.getTimes().split(BanBanConstant.BANBAN_DELIMETER)[0]) > 0){
                //如果白名单时间也未验证通过，则返回不通过
                if(!filterWhiteListTime(appointmentCountEntity, orderDate)){
                    return false;
                }
            }
        }
        //如果该时间段在黑名单时间里，则返回不通过
        if(filterBlackListTime(appointmentCountEntity, orderDate)){
            return false;
        }
        //若均通过，则返回true
        return true;
    }

    private boolean filterWhiteListTime(AppointmentCountEntity appointmentCountEntity, String orderDate){
        if(StringUtils.isEmpty(whiteListDate) || StringUtils.isEmpty(whiteListTime)){
            return false;
        }
        String[] whiteListDates = whiteListDate.split(CommonConstants.CONFIG_DELIMETER);
        String[] whiteListTimes = whiteListTime.split(CommonConstants.CONFIG_DELIMETER);
        //配置文件有误
        if(whiteListDates.length != whiteListTimes.length){
            return false;
        }
        //判断是否在白名单中
        for(int i = 0; i < whiteListDates.length; i++){
            if(StringUtils.equals(whiteListDates[i], orderDate)){
                if(DateUtils.checkTimeWithinPeroid(appointmentCountEntity.getTimes(), whiteListTimes[i])){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean filterBlackListTime(AppointmentCountEntity appointmentCountEntity, String orderDate){
        if(StringUtils.isEmpty(blackListDate) || StringUtils.isEmpty(blackListTime)){
            return false;
        }
        String[] blackListDates = blackListDate.split(CommonConstants.CONFIG_DELIMETER);
        String[] blackListTimes = blackListTime.split(CommonConstants.CONFIG_DELIMETER);
        //配置文件有误
        if(blackListDates.length != blackListTimes.length){
            return false;
        }
        //判断是否在黑名单中
        for(int i = 0; i < blackListDates.length; i++){
            if(StringUtils.equals(blackListTimes[i], orderDate)){
                if(DateUtils.checkTimeWithinPeroid(appointmentCountEntity.getTimes(), blackListTimes[i])){
                    return true;
                }
            }
        }
        return false;
    }


}
