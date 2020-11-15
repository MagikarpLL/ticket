package cn.magikarpll.ticket.business.module.banban.entity.request;

import cn.magikarpll.ticket.business.module.banban.entity.response.AppointmentCountEntity;
import cn.magikarpll.ticket.business.module.banban.entity.response.SimulatorNumberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulatorNumberRequest extends BaseBanbanRequest{

    private Integer companyId;

    private Integer roomId;

    private String planDate;

    private String time;

    @Override
    public MultiValueMap<String, String> buildRequest() {
        multiValueMap.set("companyId",companyId.toString());
        multiValueMap.set("roomId",roomId.toString());
        multiValueMap.set("planDate",planDate);
        multiValueMap.set("time",time);
        return this.multiValueMap;
    }

    public static List<SimulatorNumberRequest> convertToEntiy(List<AppointmentCountEntity> appointmentCountEntities, Integer roomId){
        List<SimulatorNumberRequest> simulatorNumberRequests = new ArrayList<>();
        for(AppointmentCountEntity appointmentCountEntity: appointmentCountEntities){
            SimulatorNumberRequest simulatorNumberRequest = new SimulatorNumberRequest();
            simulatorNumberRequest.setRoomId(roomId);
            simulatorNumberRequest.setTime(appointmentCountEntity.getTimes());
            simulatorNumberRequests.add(simulatorNumberRequest);
        }
        return simulatorNumberRequests;
    }

}
