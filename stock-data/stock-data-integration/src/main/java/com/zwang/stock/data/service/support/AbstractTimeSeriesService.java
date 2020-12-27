package com.zwang.stock.data.service.support;

import com.zwang.stock.data.configuration.StockConfiguration;
import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;

/**
 * Abstract class provides the connection to <a href="https://www.alphavantage.co/documentation/">Alpha Vantage API</a>
 * for retrieving stock data
 * <p>
 * Acknowledgements to the <a href="https://github.com/patriques82/alphavantage4j">alphavantage4j project</a> for
 * the Java wrapper
 *
 * @param <T> generic TimeSeries type
 */
public abstract class AbstractTimeSeriesService<T> extends AbstractReactiveService<T> {

    protected final StockConfiguration stockConfiguration;
    protected final TimeSeries timeSeries;

    public AbstractTimeSeriesService(StockConfiguration stockConfiguration) {
        super();
        this.stockConfiguration = stockConfiguration;
        this.timeSeries = new TimeSeries(new AlphaVantageConnector(stockConfiguration.getApiKey(), stockConfiguration.getTimeout()));
    }

    protected abstract T getTimeSeriesResponse(String symbol);

}
