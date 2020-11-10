package cn.magikarpll.ticket.business.module.banban.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanBanRequestHeader {

    private static MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

    //init
    {
        multiValueMap.set("user-agent","OXF-AN10(Android/10) WXApp(WXSample/4.5.6) Weex/0.20.0.1 1080x2277");
        multiValueMap.set("Host","sim.ctl.chelizitech.com:9901");
        multiValueMap.set("Connection","Keep-Alive");
        multiValueMap.set("Accept-Encoding","gzip");
    }

    private String ctlToken;

    private String userAgent;

    private String host;

    private String connection;

    private String acceptEncoding;

    private Integer contentLength;

    public static MultiValueMap<String, String> buildHeader(String ctlToken){
        multiValueMap.set("ctl-token", ctlToken);
        return multiValueMap;
    }

}
