package com.zwang.stock.persistence.configuration;

import com.zwang.stock.data.persistence.api.DailyAdjustedApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiClientConfiguration {

    @Bean
    public DailyAdjustedApi getDailyAdjustedApi() {
        return new DailyAdjustedApi();
    }

}
