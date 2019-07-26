package com.kish.learning.adminbootserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class AdminbootserverApplication {

    private WebClient constructWebClient(){
        WebClient webClient = null;
        try {
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
            HttpClient httpClient = HttpClient
                    .create()
                    .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
            ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
            webClient = WebClient.builder().clientConnector(connector).build();
        }catch (Exception exception){
            exception.printStackTrace(System.err);
        }
        if(webClient == null) {
            webClient = WebClient.builder().build();
        }

        return webClient;
    }


    @Bean
    public InstanceWebClient instanceWebClient(HttpHeadersProvider httpHeadersProvider,
                                               ObjectProvider<List<InstanceExchangeFilterFunction>> filtersProvider) {
        List<InstanceExchangeFilterFunction> additionalFilters = filtersProvider.getIfAvailable(Collections::emptyList);
        return InstanceWebClient.builder()
                .connectTimeout(Duration.ofMillis(10_000L))
                .readTimeout(Duration.ofMillis(10_000L))
                .defaultRetries(0)
                .retries(Collections.EMPTY_MAP)
                .httpHeadersProvider(httpHeadersProvider)
                .filters(filters -> filters.addAll(additionalFilters))
                .webClient(constructWebClient())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(AdminbootserverApplication.class, args);
    }

}
