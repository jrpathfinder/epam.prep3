package com.epam.learn.etl.process.repository;

import com.epam.learn.etl.process.model.Sport;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SportRepository extends ReactiveCrudRepository<Sport, Long> {
    Flux<Sport> findByName(String name);
}
