package com.example.socialnetworkingapp.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student spiros = new Student (
                    "Spiros",
                    "spiros@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 5)
            );

            Student alex = new Student (
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2003, Month.FEBRUARY, 8)
            );

            Student maria = new Student (
                    "Maria",
                    "maria@gmail.com",
                    LocalDate.of(2003, Month.JANUARY, 15)
            );

            List<Student> list = new LinkedList<Student>();
            list.add(spiros);
            list.add(alex);
            list.add(maria);
            repository.saveAll( list );
        };
    }

}
