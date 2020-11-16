package cn.magikarpll.ticket.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
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
    public static  String  httpPostForForm(String url, RestTemplate restTemplate, MultiValueMap<String,String> params, MultiValueMap<String,String> headers){
        try{
            HttpHeaders headersMap = new HttpHeaders();
            //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
            headersMap.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headersMap.addAll(headers);
            //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, String> paramsMap= new LinkedMultiValueMap<String, String>();
            paramsMap.addAll(params);
            //设置请求头长度
            headersMap.set("Content-Length",""+paramsMap.toString().length());
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(paramsMap, headersMap);

            //  执行HTTP请求
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode().is2xxSuccessful()){
                return response.getBody();
            }else{
                log.error("请求失败,http状态码为{},url为{}", response.getStatusCode(), url);
                return null;
            }
        }catch (RestClientException restClientException){
            log.error(restClientException.getMessage());
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * post methods  for  application/x-www-form-urlencoded
     * @param restTemplate
     */
    public static  <T> T  httpPostForJson(String url, RestTemplate restTemplate, MultiValueMap<String,String> params, MultiValueMap<String,String> headers, Class<T> t){
        try{
            HttpHeaders headersMap = new HttpHeaders();
            //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
            headersMap.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headersMap.addAll(headers);
            //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, String> paramsMap= new LinkedMultiValueMap<String, String>();
            paramsMap.addAll(params);
            //设置请求头长度
            headersMap.set("Content-Length",""+paramsMap.toString().length());
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(paramsMap, headersMap);

            //  执行HTTP请求
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode().is2xxSuccessful()){
                T result = JacksonHelper.json2pojo(response.getBody(), t);
                return result;
            }else{
                log.error("请求失败,http状态码为{},url为{}", response.getStatusCode(), url);
                return null;
            }
        }catch (RestClientException restClientException){
            log.error(restClientException.getMessage());
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static void httpGet(RestTemplate restTemplate){

    }


}
