package cn.magikarpll.ticket.business.service.impl;

import cn.magikarpll.ticket.business.module.banban.constant.BanBanConstant;
import cn.magikarpll.ticket.business.module.banban.entity.request.SaveAppointmentRequest;
import cn.magikarpll.ticket.business.module.banban.entity.request.SimulatorNumberRequest;
import cn.magikarpll.ticket.business.module.banban.entity.response.AppointmentCountEntity;
import cn.magikarpll.ticket.business.module.banban.entity.response.DeptEntity;
import cn.magikarpll.ticket.business.module.banban.entity.response.SimulatorNumberEntity;
import cn.magikarpll.ticket.business.module.banban.service.BanBanService;
import cn.magikarpll.ticket.business.service.TicketOrderService;
import cn.magikarpll.ticket.common.exception.BusinessException;
import cn.magikarpll.ticket.common.utils.DateUtils;
import cn.magikarpll.ticket.login.thread.LoginThread;
import cn.magikarpll.ticket.order.thread.DailyOrderThread;
import cn.magikarpll.ticket.order.thread.ScheduleOrderThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TicketOrderServiceImpl implements TicketOrderService {

    private static ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);

    @Value("${ticket.schedule.startDateTime}")
    private String startDateTime;

    @Value("${ticket.schedule.startTime}")
    private String startTime;

    @Resource
    private BanBanService banBanService;

    //地区常量 ID areaId
    private static final Integer[] AREA_CONSTANTS = {440303, 440304, 440305, 440309};


    @Override
    public String startOrderTicket() throws BusinessException {

//        //启动一个登录线程
//        threadPoolExecutor.scheduleAtFixedRate(new LoginThread(),0, 10, TimeUnit.SECONDS);

        //启动一个时时刷新捡漏线程
        threadPoolExecutor.scheduleAtFixedRate(new DailyOrderThread(),10, 1, TimeUnit.SECONDS);

        //启动一个每日定时抢票线程,提前30s启动
        threadPoolExecutor.scheduleAtFixedRate(new ScheduleOrderThread(), DateUtils.computeTimeDurationBeforeScheduleEveryday(startTime) - 30, DateUtils.SECONDS_PER_DAY, TimeUnit.SECONDS);

        return null;
    }

    @Override
    public String stopOrderTicket() {
        threadPoolExecutor.shutdown();

        return null;
    }

    @Override
    public String queryOrderTicket() {
        return null;
    }

    @Override
    public String exportData() throws BusinessException {
        List<DeptEntity> deptEntityList = new ArrayList<>();
        for(Integer i: AREA_CONSTANTS){
            deptEntityList.addAll(banBanService.dept(i));
        }

        //按距离远近升序筛选出的roomId list 和 对应可用时间
        List<Integer> sortedRoomId = deptEntityList.stream().filter(d -> Double.parseDouble(d.getDistance()) < 10d).sorted(Comparator.comparing(DeptEntity::getDistance)).map(d -> d.getRoomId()).collect(Collectors.toList());
        //需要序列化，给每日刷新的那个用
        List<SimulatorNumberRequest> simulatorNumberRequests = new ArrayList<>();
        for(Integer iR: sortedRoomId){
            simulatorNumberRequests.addAll(SimulatorNumberRequest.convertToEntiy(banBanService.getAppointmentCount(iR, LocalDate.now().format(DateTimeFormatter.ISO_DATE)), iR));
        }

        //需要序列化，给定时抢票的用
        List<SaveAppointmentRequest> saveAppointmentRequests = new ArrayList<>();
        for(SimulatorNumberRequest simulatorNumberRequest: simulatorNumberRequests){
            //查询 并 筛选出已备案的模拟机编号
            List<SimulatorNumberEntity> simulatorNumberEntities = banBanService.getSimulatorNumber(simulatorNumberRequest.getRoomId(),
                    LocalDate.now().format(DateTimeFormatter.ISO_DATE), simulatorNumberRequest.getTime()).stream().filter(s -> BanBanConstant.SIMULATOR_NUMBER_SET.contains(s.getName())).collect(Collectors.toList());
            saveAppointmentRequests.addAll(SaveAppointmentRequest.convertToEntiy(simulatorNumberEntities, simulatorNumberRequest));
        }

        //序列化保存simulatorNumberRequests和saveAppointmentRequests

        return null;
    }

    public static void initThreadPoolExecutor(){

    }


}
