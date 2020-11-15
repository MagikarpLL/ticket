package cn.magikarpll.ticket.business.module.banban.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCountRequest extends BaseBanbanRequest{

    private Integer companyId;

    private Integer stuid;

    private Integer roomId;

    private String planDate;

    @Override
    public MultiValueMap<String, String> buildRequest() {
        multiValueMap.set("companyId",companyId.toString());
        multiValueMap.set("stuid",stuid.toString());
        multiValueMap.set("roomId",roomId.toString());
        multiValueMap.set("PlanDate",planDate);
        return this.multiValueMap;
    }
}
