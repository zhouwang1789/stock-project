package com.zwang.stock.persistence.controller;

import com.zwang.stock.data.persistence.api.DailyAdjustedApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final DailyAdjustedApi dailyAdjustedApi;

    @PostConstruct
    public void test() throws InterruptedException {
        Thread.sleep(5000);
        log.info("Invoking ");
        dailyAdjustedApi.getDailyAdjusted("msft")
                .collectList()
                .subscribe(dailyAdjusteds -> log.info("subscribe" + dailyAdjusteds.size()));
        log.info("Finished invoking ");
    }

}
