package cn.magikarpll.ticket.business.module.banban.service.impl;

import cn.magikarpll.ticket.business.module.banban.entity.request.BanBanRequestHeader;
import cn.magikarpll.ticket.business.module.banban.entity.request.ChangeRequest;
import cn.magikarpll.ticket.business.module.banban.entity.response.*;
import cn.magikarpll.ticket.business.module.banban.service.BanBanService;
import cn.magikarpll.ticket.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
@Slf4j
@PropertySource("classpath:application.yml")
public class BanBanServiceImpl implements BanBanService {

    private static final String CHANGE_URL = "http://sim.ctl.chelizitech.com:9901/api/simulator/change";

    private static final String AREA_URL = "http://sim.ctl.chelizitech.com:9901/api/simulator/area";

    public static final String DEPT_URL = "http://sim.ctl.chelizitech.com:9901/api/simulator/dept";

    public static final String APPOINTMENT_COUNT_URL = "http://sim.ctl.chelizitech.com:9901/api/simulator/GetAppointmentCount";

    public static final String SIMULATOR_NUMBER_URL = "http://sim.ctl.chelizitech.com:9901/api/simulator/getSimulatorNumber";

    public static final String SAVE_APPOINTMENT_URL = "http://sim.ctl.chelizitech.com:9901/api/simulator/SaveAppointment";

    @Value("${banban.token}")
    private String token;

    @Resource
    private RestTemplate restTemplate;

    @Override
    @Test
    public void change() {
        MultiValueMap params = new ChangeRequest(16,421182199604230035L).buildChangeRequest();
        BanBanResponseEntity banBanResponseEntity = HttpUtils.httpPostForForm(CHANGE_URL, restTemplate ,params,
                BanBanRequestHeader.buildHeader(token),BanBanResponseEntity.class);
        ChangeEntity changeEntity = dealResponse(banBanResponseEntity,CHANGE_URL, params);
    }

    @Override
    public AreaEntity area() {
        return null;
    }

    @Override
    public DeptEntity dept() {
        return null;
    }

    @Override
    public AppointmentCountEntity getAppointmentCount() {
        return null;
    }

    @Override
    public SimulatorNumberEntity getSimulatorNumber() {
        return null;
    }

    @Override
    public Boolean saveAppointment() {
        return null;
    }

    private static <T> T dealResponse(BanBanResponseEntity banBanResponseEntity, String url, MultiValueMap params){
        if(banBanResponseEntity.getCode().equals(BanBanResponseEntity.FAILURE)){
            log.error("请求接口失败, URL为:{}, 请求参数为:{}, 返回消息为:{} ",url, params.toString(), banBanResponseEntity.getMessage());
            return null;
        }else{
            if(null != banBanResponseEntity.getResults()){
                return (T) banBanResponseEntity.getResults();
            }
            return null;
        }
    }

}
