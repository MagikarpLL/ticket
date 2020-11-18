package cn.magikarpll.ticket.business.module.banban.entity.request;

import cn.magikarpll.ticket.business.module.banban.entity.response.AppointmentCountEntity;
import cn.magikarpll.ticket.business.module.banban.entity.response.SimulatorNumberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAppointmentRequest extends BaseBanbanRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer companyId;

    private String userId;

    private Integer stuid;

    private String planDate;

    private String times;

    private String stuName;

    private Integer roomId;

    private String mobile;

    private Integer remark;

    private Integer role;

    private Integer simId;

    @Override
    public MultiValueMap<String, String> buildRequest() {
        multiValueMap.set("companyId",companyId.toString());
        multiValueMap.set("userId",userId);
        multiValueMap.set("stuid",stuid.toString());
        multiValueMap.set("PlanDate",planDate);
        multiValueMap.set("Times",times);
        multiValueMap.set("stuName",stuName);
        multiValueMap.set("roomId",roomId.toString());
        multiValueMap.set("mobile",mobile);
        multiValueMap.set("remark",remark.toString());
        multiValueMap.set("role",role.toString());
        multiValueMap.set("simId",simId.toString());
        return this.multiValueMap;
    }

    public static List<SaveAppointmentRequest> convertToEntiy(List<SimulatorNumberEntity> simulatorNumberEntities, SimulatorNumberRequest simulatorNumberRequest){
        if(null == simulatorNumberEntities){return null;}
        List<SaveAppointmentRequest> saveAppointmentRequests = new ArrayList<>();
        for(SimulatorNumberEntity simulatorNumberEntity: simulatorNumberEntities){
            SaveAppointmentRequest saveAppointmentRequest = new SaveAppointmentRequest();
            saveAppointmentRequest.setRoomId(simulatorNumberRequest.getRoomId());
            saveAppointmentRequest.setTimes(simulatorNumberRequest.getTime());
            saveAppointmentRequest.setSimId(simulatorNumberEntity.getSimId());
            saveAppointmentRequest.setPlanDate(simulatorNumberRequest.getPlanDate());
            saveAppointmentRequests.add(saveAppointmentRequest);
        }
        return saveAppointmentRequests;
    }

}
