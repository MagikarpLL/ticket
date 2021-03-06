package cn.magikarpll.ticket.business.module.banban.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequest extends BaseBanbanRequest{



    private Integer companyId;

    private Long idCardNum;

    @Override
    public MultiValueMap<String,String> buildRequest(){
        multiValueMap.set("companyId",companyId.toString());
        multiValueMap.set("idCardNum",idCardNum.toString());
        return this.multiValueMap;
    }

}
