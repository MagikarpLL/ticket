package cn.magikarpll.ticket.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HttpUtils {

    /**
     * post methods  for  application/x-www-form-urlencoded
     * @param restTemplate
     */
    public static  <T> T  httpPostForForm(String url, RestTemplate restTemplate, MultiValueMap<String,String> params, MultiValueMap<String,String> headers, Class<T> t){
        try{
            HttpHeaders headersMap = new HttpHeaders();
            //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
            headersMap.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.addAll(headers);
            //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, String> paramsMap= new LinkedMultiValueMap<String, String>();
            paramsMap.addAll(params);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(paramsMap, headersMap);
            //设置请求头长度
            headersMap.set("Content-Length",""+requestEntity.toString().length());
            //  执行HTTP请求
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, t);
            //  输出结果
            return response.getBody();
        }catch (RestClientException restClientException){
            log.error("test",restClientException);
            return null;
        }
    }

    public static void httpGet(RestTemplate restTemplate){

    }


}
