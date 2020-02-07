package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.service.BinaryService;
import com.example.service.JsonService;
import com.example.service.TextService;

import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator() {
        return builder -> {
            builder.decorator(LoggingService.newDecorator());
            builder.accessLogWriter(AccessLogWriter.combined(), false);
            builder.annotatedService(new TextService());
            builder.annotatedService(new BinaryService());
            builder.annotatedService(new JsonService());
        };
    }
}
