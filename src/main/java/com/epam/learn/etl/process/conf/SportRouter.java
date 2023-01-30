package com.epam.learn.etl.process.conf;

import com.epam.learn.etl.process.handler.SportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration(proxyBeanMethods = false)
public class SportRouter {

    @Bean
    public RouterFunction<ServerResponse> route(SportHandler sportHandler) {

        return RouterFunctions
                .route(GET("/sports"), sportHandler::getAll)
                .andRoute(POST("/sport").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), sportHandler::addOne)
                .andRoute(POST("/api/v1/sport/{sportname}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), sportHandler::addByName)
                .andRoute(GET("/api/v1/sport").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), sportHandler::searchByName);
    }
}