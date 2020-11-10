package cn.magikarpll.ticket.business.module.banban.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 接口实体for http://sim.ctl.chelizitech.com:9901/api/simulator/change
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEntity {

    private static MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

    private Integer companyId;

    private Long stuId;

    public MultiValueMap<String,String> buildChangeEntity(){
        multiValueMap.set("CompanyId",companyId.toString());
        multiValueMap.set("StuId",stuId.toString());
        return this.multiValueMap;
    }

}
