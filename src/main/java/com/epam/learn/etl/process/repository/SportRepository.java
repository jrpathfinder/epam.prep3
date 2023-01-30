package com.epam.learn.etl.process.repository;

import com.epam.learn.etl.process.model.ETLModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SportRepository extends ReactiveCrudRepository<ETLModel, Long> {
}
