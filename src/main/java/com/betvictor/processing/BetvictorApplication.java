package com.betvictor.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BetvictorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetvictorApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(){

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        RestTemplate template = new RestTemplate();
        template.setRequestFactory(requestFactory);
        return template;
    }
}
