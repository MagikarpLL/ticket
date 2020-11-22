package cn.magikarpll.ticket.business.controller;

import cn.magikarpll.ticket.business.module.banban.entity.response.ChangeEntity;
import cn.magikarpll.ticket.business.module.banban.service.BanBanService;
import cn.magikarpll.ticket.business.service.TicketOrderService;
import cn.magikarpll.ticket.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketOrderController {

    @Resource
    private TicketOrderService ticketOrderService;

    @Resource
    private BanBanService banBanService;

    @GetMapping("/start")
    public String startOrderTicket() throws BusinessException {
        ticketOrderService.startOrderTicket();
        return "start";
    }

    @GetMapping("/special")
    public String startOrderSpecial() throws BusinessException {
        ticketOrderService.startOrderTicket();
        return "start";
    }

    @GetMapping("/stop")
    public String stopOrderTicket() {
        ticketOrderService.stopOrderTicket();
        return "stop";
    }

    @GetMapping("/query")
    public String queryOrderTicket() {
        ticketOrderService.queryOrderTicket();
        return "query";
    }

    @GetMapping("/export")
    public String exportData() throws Exception {
        ticketOrderService.exportData();
        return "export";
    }

    @GetMapping("/test")
    public String test() throws Exception {
        List<ChangeEntity> changeEntityList = banBanService.change();
        log.info(changeEntityList.toString());
        return "test success";
    }

}
