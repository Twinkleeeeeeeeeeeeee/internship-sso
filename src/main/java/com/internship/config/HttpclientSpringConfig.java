package com.internship.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

/**
 * @Configuration 作用在类上，相当于配置文件的beans
 * @bean 作用在方法上相当于配置文件bean
 * @PropertySource 读取配置文件
 * @Value 获取配置文件的值
 */
@Configuration
@PropertySource(value = "classpath:httpclient.properties")
public class HttpclientSpringConfig {
    @Value("${http.maxTotal}")
    private Integer httpMaxTotal;
    @Value("${http.defaultMaxPerRoute}")
    private Integer httpDefaultMaxPerRoute;
    @Value("${http.connectTimeout}")
    private Integer httpConnectTimeout;
    @Value("${http.connectionRequestTimeout}")
    private Integer httpConnectionRequestTimeout;
    @Value("${http.socketTimeout}")
    private Integer httpSocketTimeout;

    //客户端连接管理池
    @Autowired
    private PoolingHttpClientConnectionManager manager;

    @Bean
    //设置连接池
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(httpMaxTotal);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpDefaultMaxPerRoute);
        return poolingHttpClientConnectionManager;
    }

    @Bean
    //定时清理无效连接
    public IdleConnectionEvictor IdleConnectionEvictor() {
        return new IdleConnectionEvictor(manager, 1L, TimeUnit.HOURS);
    }

    @Bean
    @Scope("prototype")
    //定义HttpClient对象，注意该对象需要设置scope="prototype":多例对象
    public CloseableHttpClient CloseableHttpClient() {
        return HttpClients.custom().setConnectionManager(this.manager).build();
    }

    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom().setConnectTimeout(httpConnectTimeout)//创建连接超时时间
                .setConnectionRequestTimeout(httpConnectionRequestTimeout)//从连接池获取连接的超时时间设置
                .setConnectTimeout(httpSocketTimeout)//数据传输的最长时间
                .build();
    }
}
