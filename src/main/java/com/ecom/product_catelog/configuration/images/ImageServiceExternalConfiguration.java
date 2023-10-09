package com.ecom.product_catelog.configuration.images;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ImageServiceExternalConfiguration {


    @Bean
    public RestTemplate restTemplate()  {
        return new RestTemplate();
    }
}
