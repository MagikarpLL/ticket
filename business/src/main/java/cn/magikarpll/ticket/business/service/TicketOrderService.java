package cn.magikarpll.ticket.business.service;

import org.springframework.web.bind.annotation.GetMapping;

public interface TicketOrderService {

    String startOrderTicket();

    String stopOrderTicket();

    String queryOrderTicket();

}
