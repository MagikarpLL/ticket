package cn.magikarpll.ticket.business.module.banban.service.impl;

import cn.magikarpll.ticket.business.module.banban.entity.request.*;
import cn.magikarpll.ticket.business.module.banban.entity.response.*;
import cn.magikarpll.ticket.business.module.banban.service.BanBanService;
import cn.magikarpll.ticket.common.exception.BusinessException;
import cn.magikarpll.ticket.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Resource(name = "httpRestTemplate")
    private RestTemplate restTemplate;


    /**
     * 参数为固定值，不需要传入
     * 返回值  companyId = 1 StuId = 10498098
     * @throws BusinessException
     */
    @Override
    public void change() throws BusinessException {
        MultiValueMap params = new ChangeRequest(16, 421182199604230035L).buildRequest();
        MultiValueMap headers = BanBanRequestHeader.buildHeader(token);
        BanBanResponseEntity banBanResponseEntity = HttpUtils.httpPostForForm(CHANGE_URL, restTemplate, params, headers
                , BanBanResponseEntity.class);
        List<ChangeEntity> changeEntity = dealResponse(banBanResponseEntity, CHANGE_URL, params);
        log.info(changeEntity.toString());
    }

    /**
     * 参数companyId为固定值，不需要传入
     * @return [{AreaOrganID=440303, AreaOragnName=罗湖区}, {AreaOrganID=440304, AreaOragnName=福田区}, {AreaOrganID=440305, AreaOragnName=南山区}, {AreaOrganID=440306, AreaOragnName=宝安区}, {AreaOrganID=440307, AreaOragnName=龙岗区}, {AreaOrganID=440308, AreaOragnName=盐田区}, {AreaOrganID=440309, AreaOragnName=龙华区}, {AreaOrganID=440310, AreaOragnName=坪山区}, {AreaOrganID=440311, AreaOragnName=光明区}, {AreaOrganID=440312, AreaOragnName=大鹏新区}]
     * @throws BusinessException
     */
    @Override
    public AreaEntity area() throws BusinessException {
        MultiValueMap params = new AreaRequest(1).buildRequest();
        MultiValueMap headers = BanBanRequestHeader.buildHeader(token);
        BanBanResponseEntity banBanResponseEntity = HttpUtils.httpPostForForm(AREA_URL, restTemplate, params, headers
                , BanBanResponseEntity.class);
        List<AreaEntity> areaEntities = dealResponse(banBanResponseEntity, AREA_URL, params);
        log.info(areaEntities.toString());
        return null;
    }

    /**
     * 传入参数areaId,其他参数都为固定值
     * 返回结果中有用的应该只有下面三个
     * [{RoomId=46, Areaid=40, Distance=10.5}, {RoomId=331, Areaid=40, Distance=23.6}]
     * @return
     * @throws BusinessException
     */
    @Override
    public List<DeptEntity> dept(Integer areaId) throws BusinessException {
        MultiValueMap params = new DeptRequest(areaId,"22.548093","114.05115",1).buildRequest();
        MultiValueMap headers = BanBanRequestHeader.buildHeader(token);
        BanBanResponseEntity banBanResponseEntity = HttpUtils.httpPostForForm(DEPT_URL, restTemplate, params, headers
                , BanBanResponseEntity.class);
        List<DeptEntity> deptEntities = dealResponse(banBanResponseEntity, DEPT_URL, params);
        log.info(deptEntities.toString());
        return deptEntities;
    }

    /**
     * roomId和planDate需要传入  其他为固定值
     * 返回值为可预定的时间段数组  {"Times":"14:00-15:00","HadOrder":1,"CanOrder":0,"State":"0"}
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AppointmentCountEntity> getAppointmentCount(Integer roomId, String planDate) throws BusinessException {
        MultiValueMap params = new AppointmentCountRequest(1,10498098,roomId,planDate).buildRequest();
        MultiValueMap headers = BanBanRequestHeader.buildHeader(token);
        BanBanResponseEntity banBanResponseEntity = HttpUtils.httpPostForForm(APPOINTMENT_COUNT_URL, restTemplate, params, headers
                , BanBanResponseEntity.class);
        List<AppointmentCountEntity> appointmentCountEntities = dealResponse(banBanResponseEntity, APPOINTMENT_COUNT_URL, params);
        log.info(appointmentCountEntities.toString());
        return null;
    }

    /**
     * roomId , planDate和time需要传入，其他为固定值
     * 返回值为可预订的模拟机数据 {"SimId":224,"name":"SG-185","isOrder":1}
     * @return
     * @throws BusinessException
     */
    @Override
    public List<SimulatorNumberEntity> getSimulatorNumber(Integer roomId, String planDate, String time) throws BusinessException {
        MultiValueMap params = new SimulatorNumberRequest(1,roomId,planDate,time).buildRequest();
        MultiValueMap headers = BanBanRequestHeader.buildHeader(token);
        BanBanResponseEntity banBanResponseEntity = HttpUtils.httpPostForForm(SIMULATOR_NUMBER_URL, restTemplate, params, headers
                , BanBanResponseEntity.class);
        List<SimulatorNumberEntity> simulatorNumberEntities = dealResponse(banBanResponseEntity, SIMULATOR_NUMBER_URL, params, SimulatorNumberEntity.class);
        log.info(simulatorNumberEntities.toString());
        return simulatorNumberEntities;
    }

    /**
     * planDate times roomId simId 需要传入  其他为固定值
     * @return
     * @throws BusinessException
     */
    @Override
    public Boolean saveAppointment() throws BusinessException {
        MultiValueMap params = new SaveAppointmentRequest(1,"202009242330012b53148eed5c496383f6e83590e10f5a",10498098,"2020-11-15","14:00-15:00","雷振",267,"13006189736",7,5,224).buildRequest();
        MultiValueMap headers = BanBanRequestHeader.buildHeader(token);
        BanBanResponseEntity banBanResponseEntity = HttpUtils.httpPostForForm(SAVE_APPOINTMENT_URL, restTemplate, params, headers
                , BanBanResponseEntity.class);
        dealResponse(banBanResponseEntity, SIMULATOR_NUMBER_URL, params, null);
        return null;
    }

    private static <T,K> T dealResponse(BanBanResponseEntity banBanResponseEntity, String url, MultiValueMap params, K k) throws BusinessException {
        if (!banBanResponseEntity.getCode().equals(BanBanResponseEntity.SUCCESS)) {
            log.error("请求接口失败, URL为:{}, 请求参数为:{}, 返回消息为:{} ", url, params.toString(), banBanResponseEntity.getMessage());
            throw new BusinessException("请求失败!");
        } else {
            if (null != banBanResponseEntity.getResult()) {


                return (T) banBanResponseEntity.getResult();
            }
            return null;
        }
    }

}
