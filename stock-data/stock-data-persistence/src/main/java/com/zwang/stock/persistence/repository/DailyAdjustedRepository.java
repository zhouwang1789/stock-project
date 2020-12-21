package com.zwang.stock.persistence.repository;

import com.zwang.stock.data.persistence.model.DailyAdjusted;
import com.zwang.stock.persistence.repository.support.TimeSeriesBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyAdjustedRepository extends TimeSeriesBaseRepository<DailyAdjusted, Long> {
}
