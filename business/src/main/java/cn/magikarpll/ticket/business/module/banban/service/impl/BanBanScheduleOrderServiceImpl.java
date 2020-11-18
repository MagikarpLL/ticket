package cn.magikarpll.ticket.business.module.banban.service.impl;

import cn.magikarpll.ticket.order.async.service.impl.AbstractScheduleOrderServiceImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class BanBanScheduleOrderServiceImpl extends AbstractScheduleOrderServiceImpl {
    @Override
    public void scheduleOrder() {

    }
}
