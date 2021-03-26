package com.epam.conference.sparkdatauser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Evgeny Borisov
 */
@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApp.class);
        PersonRepository personRepository = context.getBean(PersonRepository.class);
        personRepository.findByAgeGreaterThanAndNameContainsSortByNameAndAge(30,"ov").forEach(System.out::println);
//        personRepository.findByAgeGreaterThan(30).forEach(System.out::println);
        System.out.println();
    }
}
