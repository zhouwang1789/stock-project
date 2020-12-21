package com.zwang.stock.data.service.support;

import com.zwang.stock.data.configuration.StockConfiguration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

public abstract class AbstractReactiveService<T> extends AbstractTimeSeriesService<T> {

    private Scheduler scheduler;

    @PostConstruct
    public void postConstruct() {
        this.scheduler = Schedulers.elastic();
    }

    public AbstractReactiveService(StockConfiguration stockConfiguration) {
        super(stockConfiguration);
    }

    protected <R> Mono<R> reactiveGet(Supplier<R> supplier) {
        return Mono.fromSupplier(supplier)
                .subscribeOn(scheduler);
    }
}
