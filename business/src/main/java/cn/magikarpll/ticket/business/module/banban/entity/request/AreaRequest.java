package cn.magikarpll.ticket.business.module.banban.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaRequest extends BaseBanbanRequest{

    private Integer companyId;

    @Override
    public MultiValueMap<String,String> buildRequest(){
        multiValueMap.set("companyId",companyId.toString());
        return this.multiValueMap;
    }

}
