package cn.magikarpll.ticket.business.service.impl;

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
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class TicketOrderServiceImpl implements TicketOrderService {

    private static ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);

    @Value("${ticket.schedule.startDateTime}")
    private String startDateTime;

    @Value("${ticket.schedule.startTime}")
    private String startTime;

    @Resource
    private BanBanService banBanService;


    @Override
    public String startOrderTicket() throws BusinessException {

//        //启动一个登录线程
//        threadPoolExecutor.scheduleAtFixedRate(new LoginThread(),0, 10, TimeUnit.SECONDS);
//
//        //启动一个时时刷新捡漏线程
//        threadPoolExecutor.scheduleAtFixedRate(new DailyOrderThread(),10, 1, TimeUnit.SECONDS);
//
//        //启动一个每日定时抢票线程,提前30s启动
//        threadPoolExecutor.scheduleAtFixedRate(new ScheduleOrderThread(), DateUtils.computeTimeDurationBeforeScheduleEveryday(startTime) - 30, DateUtils.SECONDS_PER_DAY, TimeUnit.SECONDS);

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

    public static void initThreadPoolExecutor(){

    }


}
