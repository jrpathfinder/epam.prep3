package com.epam.learn.etl.process;

import com.epam.learn.etl.process.model.Sport;
import com.epam.learn.etl.process.repository.SportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(SportRepository repository) {

		return args -> {
			// save a few sports
			repository.saveAll(Arrays.asList(new Sport("1", "Climbing"),
							new Sport("2", "Running")))
					.blockLast(Duration.ofSeconds(10));

			// fetch all sports
			log.info("Sports found with findAll():");
			log.info("-------------------------------");
			repository.findAll().doOnNext(sport -> log.info(sport.toString())).blockLast(Duration.ofSeconds(10));

		};
	}
}
