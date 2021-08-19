package com.example.socialnetworkingapp.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            Usr spiros = new Usr (
                    "Spiros",
                    "Chalkias",
                    "spiros@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 5),
                    "6987654321",
                    "asdf"
            );

            Usr alex = new Usr (
                    "Alex",
                    "Alexakis",
                    "alex@gmail.com",
                    LocalDate.of(2003, Month.FEBRUARY, 8),
                    "6978675645",
                    "asdf"
            );

            List<Usr> list = new LinkedList<Usr>();
            list.add(spiros);
            list.add(alex);
            repository.saveAll( list );
        };
    }

}