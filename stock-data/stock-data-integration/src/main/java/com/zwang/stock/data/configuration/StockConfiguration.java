package com.zwang.stock.data.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.zwang.stock")
@Getter
@Setter
public class StockConfiguration {

    private String apiKey;
    private int timeout;
    private String size;
}
