package cn.magikarpll.ticket.order.async.service.impl;

import cn.magikarpll.ticket.order.async.service.DailyOrderService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

public abstract class AbstractDailyOrderServiceImpl implements DailyOrderService {

    public abstract void dailyOrder() throws Exception;

}
