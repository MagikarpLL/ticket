package cn.magikarpll.ticket.business.service;

import cn.magikarpll.ticket.common.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;

public interface TicketOrderService {

    String startOrderTicket() throws BusinessException;

    String stopOrderTicket();

    String queryOrderTicket();

    String exportData() throws BusinessException, Exception;

    String startOrderSpecial() throws Exception;

}
