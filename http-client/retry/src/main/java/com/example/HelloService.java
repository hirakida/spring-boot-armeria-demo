package com.example;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;

@Component
public class HelloService {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Get("/hello")
    public HttpResult<String> hello() {
        final int count = COUNTER.incrementAndGet();
        HttpStatus status = count % 3 == 0 ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        return HttpResult.of(status, "Hello!");
    }
}