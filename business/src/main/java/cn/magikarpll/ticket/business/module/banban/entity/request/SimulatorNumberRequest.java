package cn.magikarpll.ticket.business.module.banban.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

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
}
