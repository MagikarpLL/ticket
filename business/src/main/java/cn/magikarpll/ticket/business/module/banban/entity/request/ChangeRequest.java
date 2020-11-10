package cn.magikarpll.ticket.business.module.banban.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequest {

    private static MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

    private Integer companyId;

    private Long idCardNum;

    public MultiValueMap<String,String> buildChangeRequest(){
        multiValueMap.set("CompanyId",companyId.toString());
        multiValueMap.set("StuId",idCardNum.toString());
        return this.multiValueMap;
    }

}
