package cn.magikarpll.ticket.business.module.banban.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptRequest extends BaseBanbanRequest{

    private Integer areaId;

    private String lat;

    private String lng;

    private Integer companyId;

    @Override
    public MultiValueMap<String, String> buildRequest() {
        multiValueMap.set("areaId",areaId.toString());
        multiValueMap.set("Lat",lat);
        multiValueMap.set("Lng",lng);
        multiValueMap.set("companyId",companyId.toString());
        return this.multiValueMap;
    }
}
