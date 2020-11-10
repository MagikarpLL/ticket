package cn.magikarpll.ticket.business.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.HttpClient;

/**
 * Created by gao on 2018/12/10.
 * 声明并初始化连接池用于调用外部服务接口
 */
@Configuration
public class RestTemplateConfig {

    @Value("${httpclient.maxtotal}")
    public Integer maxTotal;
    @Value("${httpclient.defaultMaxPerRoute}")
    public Integer defaultMaxPerRoute;
    /**
     * 连接上服务器(握手成功)的时间，超出抛出connect timeout
     */
    @Value("${httpclient.connectTimeout}")
    public Integer connectTimeout;
    /**
     * 从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
     */
    @Value("${httpclient.connectionRequestTimeout}")
    public Integer connectionRequestTimeout;
    /**
     * 服务器返回数据(response)的时间，超过抛出read timeout
     */
    @Value("${httpclient.socketTimeout}")
    public Integer socketTimeout;
    @Value("${httpclient.validateAfterInactivity}")
    public Integer validateAfterInactivity;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setValidateAfterInactivity(validateAfterInactivity);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout) //服务器返回数据(response)的时间，超过抛出read timeout
                .setConnectTimeout(connectTimeout) //连接上服务器(握手成功)的时间，超出抛出connect timeout
                .setConnectionRequestTimeout(connectionRequestTimeout)//从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }
}
