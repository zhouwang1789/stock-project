package com.zwang.stock.data.mapper;

import com.zwang.stock.data.model.DailyAdjusted;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.patriques.output.timeseries.data.StockData;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface DailyAdjustedMapper extends BaseMapper {

    List<DailyAdjusted> stockDataListToDailyAdjusteds(List<StockData> stockDataList);

    @Mapping(target = "dateTime", source = "dateTime", qualifiedByName = "localDateTimeToOffsetDateTime")
    DailyAdjusted stockDataToDailyAdjusted(StockData stockData);

}
