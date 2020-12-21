package com.zwang.stock.persistence.repository.support;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface TimeSeriesBaseRepository<T, ID> extends ReactiveCrudRepository<T, ID> {

}
