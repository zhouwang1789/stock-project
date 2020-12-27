package com.zwang.stock.data.controller;

import com.zwang.stock.data.api.StockApi;
import com.zwang.stock.data.model.DailyAdjusted;
import com.zwang.stock.data.service.DailyAdjustedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StockController implements StockApi {

    private final DailyAdjustedService dailyAdjustedService;

    @Override
    public Mono<ResponseEntity<Flux<DailyAdjusted>>> getDailyAdjusted(String symbol, ServerWebExchange exchange) {
        if (StringUtils.isEmpty(symbol)) {
            return Mono.just(ResponseEntity.badRequest().body(Flux.empty()));
        }

        Instant start = Instant.now();
        return Mono.just(symbol)
                .doOnNext(s -> log.debug("Requesting dailyAdjusted for symbol: {}", s))
                .flatMap(dailyAdjustedService::getDailyAdjusted)
                .doOnError(throwable -> log.error("Error occurs for getting dailyAdjusteds", throwable))
                .doOnNext(dailyAdjusteds -> log.info("Retrieved dailyAdjusteds.size[{}] for symbol:[{}] in {} ms", dailyAdjusteds.size(), symbol, Duration.between(start, Instant.now()).toMillis()))
                .flatMap(dailyAdjusteds -> Mono.just(ResponseEntity.ok(Flux.fromIterable(dailyAdjusteds))));
    }

}