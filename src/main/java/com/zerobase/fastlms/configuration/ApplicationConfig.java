package com.zerobase.fastlms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class ApplicationConfig {
    @Bean
    public CommonsMultipartResolver commonsMultipartResolver(){
        return new CommonsMultipartResolver();
    }
}
