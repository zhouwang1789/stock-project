package com.zwang.stock.data.service;

import com.zwang.stock.data.model.DailyAdjusted;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DailyAdjustedService {

    Mono<List<DailyAdjusted>> getDailyAdjusted(String symbol);

}
