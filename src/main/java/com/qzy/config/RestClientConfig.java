package com.qzy.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfig {

    @Value("${elasticsearch.cluster-name}")
    private String clusterName;
    @Value("${elasticsearch.cluster-node}")
    private String clusterNodes;

    @Bean
    public RestClientBuilder restClientBuilder() {
        HttpHost[] hosts = getHosts(clusterNodes);
        RestClientBuilder builder = RestClient.builder(hosts);
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                return httpAsyncClientBuilder.setDefaultIOReactorConfig(
                        IOReactorConfig.custom().setIoThreadCount(1).build()
                );
            }
        });
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                return builder.setSocketTimeout(10000);
            }
        });
        return builder;
    }

    private HttpHost[] getHosts(String clusterNodes) {
        String[] nodes = clusterNodes.split(",");
        HttpHost[] hosts = new HttpHost[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            HttpHost httpHost = new HttpHost(nodes[i], 9200, "http");
            hosts[i] = httpHost;
        }
        return hosts;
    }

    @Bean
    public RestClient restClient(@Autowired RestClientBuilder restClientBuilder) {
        return restClientBuilder.build();
    }

}
