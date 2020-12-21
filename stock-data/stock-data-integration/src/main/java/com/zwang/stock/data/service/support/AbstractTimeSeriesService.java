package com.zwang.stock.data.service.support;

import com.zwang.stock.data.configuration.StockConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractTimeSeriesService<T> {

    protected final StockConfiguration stockConfiguration;
    protected TimeSeries timeSeries;

    @PostConstruct
    public void init() {
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(stockConfiguration.getApiKey(), stockConfiguration.getTimeout());
        timeSeries = new TimeSeries(apiConnector);
    }

    public T getTimeSeries(String symbol) {
        T response = getTimeSeriesResponse(symbol);
        return Optional.ofNullable(response).orElseThrow(() ->
                (new IllegalArgumentException("TimeSeries metadata is null for " + symbol)));
    }

    protected abstract T getTimeSeriesResponse(String symbol);

}
