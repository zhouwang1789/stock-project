package com.zwang.stock.data.service.impl;

import com.zwang.stock.data.configuration.StockConfiguration;
import com.zwang.stock.data.mapper.DailyAdjustedMapper;
import com.zwang.stock.data.service.DailyAdjustedService;
import com.zwang.stock.data.service.support.AbstractTimeSeriesService;
import lombok.extern.slf4j.Slf4j;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.timeseries.DailyAdjusted;
import org.patriques.output.timeseries.TimeSeriesResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class DailyAdjustedServiceImpl extends AbstractTimeSeriesService<TimeSeriesResponse> implements DailyAdjustedService {

    private final DailyAdjustedMapper dailyAdjustedMapper;

    public DailyAdjustedServiceImpl(StockConfiguration stockConfiguration, DailyAdjustedMapper dailyAdjustedMapper) {
        super(stockConfiguration);
        this.dailyAdjustedMapper = dailyAdjustedMapper;
    }

    @Override
    protected DailyAdjusted getTimeSeriesResponse(String symbol) {
        OutputSize outputSize = stockConfiguration.getOutputSize().equals(OutputSize.FULL.getValue())
                ? OutputSize.FULL : OutputSize.COMPACT;
        return timeSeries.dailyAdjusted(symbol, outputSize);
    }

    @Override
    public Mono<List<com.zwang.stock.data.model.DailyAdjusted>> getDailyAdjusted(String symbol) {
        return reactiveGet(() -> getTimeSeriesResponse(symbol))
                .doOnNext(dailyAdjusted -> log.debug("Received dailyAdjusted.size:[{}] from external API call", dailyAdjusted.getStockData().size()))
                .flatMap(dailyAdjusted -> Mono.defer(() -> Mono.just(dailyAdjusted.getStockData())))
                .flatMap(stockData -> Mono.just(dailyAdjustedMapper.stockDataListToDailyAdjusteds(stockData)));
    }
}
