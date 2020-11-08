package cn.magikarpll.ticket.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketOrderController {

    @GetMapping("/start")
    public String startOrderTicket() {
        return "start";
    }

    @GetMapping("/stop")
    public String stopOrderTicket() {
        return "stop";
    }

    @GetMapping("/query")
    public String queryOrderTicket() {
        return "query";
    }

}
