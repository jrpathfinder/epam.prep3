package com.epam.learn.etl.process.controller;

import com.epam.learn.etl.process.model.ETLModel;
import com.epam.learn.etl.process.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sports")
@RequiredArgsConstructor
public class SportController {

    private final SportRepository repository;

    @GetMapping("/{id}")
    public Mono<ETLModel> getEtlModelById(@PathVariable String id) {
        return repository.findById(Long.valueOf(id));
    }

    @GetMapping
    public Flux<ETLModel> getAllSports() {
        return repository.findAll();
    }
}
