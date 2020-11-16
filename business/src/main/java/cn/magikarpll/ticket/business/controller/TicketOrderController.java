package cn.magikarpll.ticket.business.controller;

import cn.magikarpll.ticket.business.service.TicketOrderService;
import cn.magikarpll.ticket.common.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ticket")
public class TicketOrderController {

    @Resource
    private TicketOrderService ticketOrderService;

    @GetMapping("/start")
    public String startOrderTicket() throws BusinessException {
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
    public String exportData() throws BusinessException {
        ticketOrderService.exportData();
        return "export";
    }

}
