package com.epam.learn.etl.process.service;

import com.epam.learn.etl.process.model.Sport;
import com.epam.learn.etl.process.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SportService {
    private final SportRepository repository;
    public Flux<Sport> list(){
        return repository.findAll();
    }

    public Mono<Sport> addOne(Sport sport){
        return repository.save(sport);
    }

    public Flux<Sport> search(String name){
        return repository
                .findByName(name);
    }
}
