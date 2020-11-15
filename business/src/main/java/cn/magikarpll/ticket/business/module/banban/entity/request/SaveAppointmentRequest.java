package cn.magikarpll.ticket.business.module.banban.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAppointmentRequest extends BaseBanbanRequest{

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
}
