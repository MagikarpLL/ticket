package cn.magikarpll.ticket.business.module.banban.entity.request;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public abstract class BaseBanbanRequest {

    protected MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

    public abstract MultiValueMap<String,String> buildRequest();

}
