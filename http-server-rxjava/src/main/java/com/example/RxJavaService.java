package com.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesEventStream;
import com.linecorp.armeria.server.annotation.ProducesJson;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJavaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RxJavaService.class);

    @Get("/single")
    @ProducesJson
    public Single<LocalDateTime> single() {
        return Single.just(LocalDateTime.now());
    }

    @Get("/maybe")
    @ProducesJson
    public Maybe<LocalDateTime> maybe() {
        return Maybe.just(LocalDateTime.now());
    }

    @Get("/flowable")
    @ProducesJson
    public Flowable<LocalTime> flowable() {
        return Flowable.range(1, 5)
                       .concatMap(i -> Flowable.fromCallable(LocalTime::now)
                                               .delay(100, TimeUnit.MILLISECONDS))
                       .doOnNext(time -> LOGGER.info("{}", time));
    }

    @Get("/sse")
    @ProducesEventStream
    public Publisher<ServerSentEvent> sse(ServiceRequestContext ctx) {
        ExecutorService executor = ctx.eventLoop();
        return Flowable.interval(100, TimeUnit.MILLISECONDS)
                       .take(5)
                       .observeOn(Schedulers.from(executor))
                       .doOnNext(i -> LOGGER.info("{}", i))
                       .scan(Long::sum)
                       .map(data -> ServerSentEvent.ofData(data.toString()));
    }
}
