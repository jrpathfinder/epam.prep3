package com.epam.learn.etl.process.handler;

import com.epam.learn.etl.process.model.Sport;
import com.epam.learn.etl.process.service.SportService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Component
@RequiredArgsConstructor
public class SportHandler {

    private final SportService sportService;

    public Mono<ServerResponse> getAll(ServerRequest request) {

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(sportService.list(), Sport.class);
    }

    public Mono<ServerResponse> addOne(ServerRequest request) {

        final Mono<Sport> sport = request.bodyToMono(Sport.class);
        final UUID id = UUID.randomUUID();
        return created(UriComponentsBuilder.fromPath("sport/" + id).build().toUri())
                .contentType(APPLICATION_JSON)
                .body(
                        fromPublisher(
                                sport
                                        .map(p -> new Sport(id.toString(), p.getName()))
                                        .flatMap(sportService::addOne), Sport.class));
    }

    public Mono<ServerResponse> addByName(ServerRequest request) {

        final var sportName = request.pathVariable("sportname");
        final var alreadyExists = Mono.from(sportService.search(sportName));
        alreadyExists.log()
                .subscribe(new Subscriber<Sport>() {
                    private Subscription s;
                    int onNextAmount;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        s.request(2);
                    }

                    @Override
                    public void onNext(Sport sport) {
                        onNextAmount++;
                        if (onNextAmount % 2 == 0) {
                            s.request(2);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });


        final var id = UUID.randomUUID();
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(alreadyExists
                        .flatMap(Mono::just)
                        .switchIfEmpty(Mono
                                .defer(() -> sportService.addOne(new Sport(id.toString(), sportName)))), Sport.class);
    }

    public Mono<ServerResponse> searchByName(ServerRequest request) {

        final var sports = request.queryParam("q")
                .map(sportService::search).orElseThrow();
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(sports, Sport.class);
    }

}
