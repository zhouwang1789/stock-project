package com.zwang.stock.data.service.support;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.function.Supplier;

/**
 * Abstract class allows for asynchronous requests using reactive programming
 *
 * @param <T> generic Stock data type
 * @author zwang
 */
public abstract class AbstractReactiveService<T> {

    private final Scheduler scheduler;

    public AbstractReactiveService() {
        this.scheduler = Schedulers.elastic();
    }

    protected <R> Mono<R> reactiveGet(Supplier<R> supplier) {
        return Mono.fromSupplier(supplier)
                .subscribeOn(scheduler);
    }

}
