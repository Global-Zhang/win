package com.hereWeGo.rpc.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

/*  Elasticsearch 配置类 */
@Configuration
public class EsConfig {

    //ES服务器地址
    @Value("${elasticsearch.address}")
    private String[] address;
    //ES服务器连接方式
    private static final String SCHEME = "http";

    public EsConfig(){}
    /*
    * 根据服务器地址构建HttpHost对象
    * */
    @SuppressWarnings("all")
    @Bean
    public HttpHost builderHttpHost(String s){
        String[] address = s.split(":");
        if (2 != address.length){
            return null;
        }
        String host = address[0];
        Integer pot = Integer.valueOf(address[1]);
        return new HttpHost(host,pot,SCHEME);
    }

    /*
    * 创建RestClientBuilder对象
    * */
    @Bean
    public RestClientBuilder restClientBuilder(){
        HttpHost[] hosts = Arrays.stream(address)
                .map(this::builderHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        return RestClient.builder(hosts);
    }

    /*
    * 创建RestHighLevelClient对象
    * */
    @Bean
    public RestHighLevelClient restHighLevelClient(@Autowired RestClientBuilder restClientBuilder){

        return new RestHighLevelClient(restClientBuilder);
    }
}
