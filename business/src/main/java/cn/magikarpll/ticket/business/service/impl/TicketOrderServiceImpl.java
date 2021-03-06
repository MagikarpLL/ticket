package cn.magikarpll.ticket.business.service.impl;

import cn.magikarpll.ticket.business.module.banban.constant.BanBanConstant;
import cn.magikarpll.ticket.business.module.banban.entity.request.AppointmentCountRequest;
import cn.magikarpll.ticket.business.module.banban.entity.request.SaveAppointmentRequest;
import cn.magikarpll.ticket.business.module.banban.entity.request.SimulatorNumberRequest;
import cn.magikarpll.ticket.business.module.banban.entity.response.DeptEntity;
import cn.magikarpll.ticket.business.module.banban.entity.response.SimulatorNumberEntity;
import cn.magikarpll.ticket.business.module.banban.service.BanBanService;
import cn.magikarpll.ticket.business.service.TicketOrderService;
import cn.magikarpll.ticket.common.exception.BusinessException;
import cn.magikarpll.ticket.common.utils.DateUtils;
import cn.magikarpll.ticket.common.utils.StreamUtils;
import cn.magikarpll.ticket.order.async.service.DailyOrderService;
import cn.magikarpll.ticket.order.async.service.ScheduleOrderService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TicketOrderServiceImpl implements TicketOrderService {

    private static ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);

    @Value("${ticket.banban.schedule.query-date}")
    private String queryDate;

    @Resource
    private BanBanService banBanService;

    //地区常量 ID areaId
    //罗湖区， 福田区， 南山区， 宝安区，龙岗区， 龙华区
    private static final Integer[] AREA_CONSTANTS = {440303, 440304, 440305, 440306, 440307, 440309};


    @Override
    public String startOrderTicket() throws BusinessException {

//        //启动一个时时刷新捡漏线程
//        threadPoolExecutor.schedule(new DailyOrderService(),10, TimeUnit.SECONDS);
//
//        //启动一个每日定时抢票线程,提前30s启动
//        threadPoolExecutor.scheduleAtFixedRate(new ScheduleOrderService(), DateUtils.computeTimeDurationBeforeScheduleEveryday(startTime) - 30, DateUtils.SECONDS_PER_DAY, TimeUnit.SECONDS);

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
    public String exportData() throws Exception {
        List<DeptEntity> deptEntityList = new ArrayList<>();
        List<DeptEntity> tempDeptEntities = null;
        List<SimulatorNumberRequest> tempSimulatorNumberRequests = null;
        for (Integer i : AREA_CONSTANTS) {
            tempDeptEntities = banBanService.dept(i);
            if (null != tempDeptEntities) {
                deptEntityList.addAll(tempDeptEntities);
            }
        }

        //按距离远近升序筛选出的roomId list 和 对应可用时间
        List<Integer> sortedRoomId = deptEntityList.stream()
                .filter(d -> Double.parseDouble(d.getDistance()) < 17d)
                .sorted(Comparator.comparing(DeptEntity::getDistance))
                .map(d -> d.getRoomId())
                .collect(Collectors.toList());
        //需要序列化，给每日刷新的那个用
        List<SimulatorNumberRequest> simulatorNumberRequests = new ArrayList<>();
        for (Integer iR : sortedRoomId) {
            tempSimulatorNumberRequests = SimulatorNumberRequest.convertToEntiy(banBanService.getAppointmentCount(iR, queryDate), iR, queryDate);
            if (null != tempSimulatorNumberRequests) {
                simulatorNumberRequests.addAll(tempSimulatorNumberRequests);
                tempSimulatorNumberRequests = null;
            }
        }

        //TODO
        //需要序列化，给定时抢票的用
        List<SaveAppointmentRequest> saveAppointmentRequests = new ArrayList<>();
        for (SimulatorNumberRequest simulatorNumberRequest : simulatorNumberRequests) {
            //查询 并 筛选出已备案的模拟机编号
            List<SimulatorNumberEntity> tempSimulatorNumberEntityList = banBanService.getSimulatorNumber(simulatorNumberRequest.getRoomId(),
                    queryDate, simulatorNumberRequest.getTime());
            List<SimulatorNumberEntity> simulatorNumberEntities = null;
            if (null != tempSimulatorNumberEntityList) {
                simulatorNumberEntities = tempSimulatorNumberEntityList.stream().filter(s -> BanBanConstant.SIMULATOR_NUMBER_SET.contains(s.getName())).collect(Collectors.toList());
            }
            if (null != simulatorNumberEntities) {
                saveAppointmentRequests.addAll(SaveAppointmentRequest.convertToEntiy(simulatorNumberEntities, simulatorNumberRequest));
            }
        }

        //序列化保存simulatorNumberRequests和saveAppointmentRequests
        StreamUtils.writeObject(sortedRoomId, new File("sortedRoomId"));
        StreamUtils.writeObject(simulatorNumberRequests, new File("simulatorNumberRequests"));
        StreamUtils.writeObject(saveAppointmentRequests, new File("saveAppointmentRequests"));
        return "success";
    }

    @Override
    @Scheduled(cron = "59 29 08 * * *")
    @Test
    public String startOrderSpecial() throws Exception {
        log.info("抢票开始，时间为: " + System.currentTimeMillis());
        Thread.currentThread().sleep(900);
        //单独构造两个特殊的请求，一个抢工作日中午11:00 - 12:00 一个抢晚上18-19:00
        //List<SaveAppointmentRequest> saveAppointmentRequests = StreamUtils.readObjectForList(new File("saveAppointmentRequests"));
        SaveAppointmentRequest saveAppointmentRequestA1 = new SaveAppointmentRequest();
        saveAppointmentRequestA1.setPlanDate("2020-11-24");
        saveAppointmentRequestA1.setTimes("11:00-12:00");
        saveAppointmentRequestA1.setRoomId(337);
        saveAppointmentRequestA1.setSimId(248);

        SaveAppointmentRequest saveAppointmentRequestA2 = new SaveAppointmentRequest();
        saveAppointmentRequestA2.setPlanDate("2020-11-24");
        saveAppointmentRequestA2.setTimes("18:00-19:00");
        saveAppointmentRequestA2.setRoomId(337);
        saveAppointmentRequestA2.setSimId(248);

        SaveAppointmentRequest saveAppointmentRequestB1 = new SaveAppointmentRequest();
        saveAppointmentRequestB1.setPlanDate("2020-11-24");
        saveAppointmentRequestB1.setTimes("11:00-12:00");
        saveAppointmentRequestB1.setRoomId(337);
        saveAppointmentRequestB1.setSimId(256);

        SaveAppointmentRequest saveAppointmentRequestB2 = new SaveAppointmentRequest();
        saveAppointmentRequestB2.setPlanDate("2020-11-24");
        saveAppointmentRequestB2.setTimes("18:00-19:00");
        saveAppointmentRequestB2.setRoomId(337);
        saveAppointmentRequestB2.setSimId(256);

        while (1 == 1) {
            if (LocalTime.now().getSecond() == 35) {
                break;
            }
            Boolean a1, a2, b1, b2 = null;
            try {
                a1 = banBanService.saveAppointment(saveAppointmentRequestA1);
                log.info("A1抢票结束，时间为: " + System.currentTimeMillis());
                if (null != a1 && a1 == true) {
                    break;
                }
            } catch (BusinessException businessException) {

            }
            try {
                a2 = banBanService.saveAppointment(saveAppointmentRequestA2);
                log.info("A2抢票结束，时间为: " + System.currentTimeMillis());
                if (null != a2 && a2 == true) {
                    break;
                }
            } catch (BusinessException businessException) {

            }
            try {
                b1 = banBanService.saveAppointment(saveAppointmentRequestB1);
                log.info("B1抢票结束，时间为: " + System.currentTimeMillis());
                if (null != b1 && b1 == true) {
                    break;
                }
            } catch (BusinessException businessException) {

            }
            try {
                b2 = banBanService.saveAppointment(saveAppointmentRequestB2);
                log.info("B2抢票结束，时间为: " + System.currentTimeMillis());
                if (null != b2 && b2 == true) {
                    break;
                }
            } catch (BusinessException businessException) {

            }
            Thread.currentThread().sleep((int)(Math.random()*10+1));
        }
        return null;
    }

    public static void initThreadPoolExecutor() {

    }

}
