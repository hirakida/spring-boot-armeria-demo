package com.example.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.Get;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockingService {

    @Blocking
    @Get("/blocking1")
    public String blocking1(ServiceRequestContext ctx) {
        log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        sleep(1);
        log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        return "Hello!";
    }

    @Get("/blocking2")
    public HttpResponse blocking2(ServiceRequestContext ctx) {
        log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());

        CompletableFuture<HttpResponse> future = new CompletableFuture<>();
        ctx.blockingTaskExecutor().execute(() -> {
            sleep(1);
            log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
            future.complete(HttpResponse.of("Hello!"));
        });
        return HttpResponse.from(future);
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ignored) { }
    }
}
