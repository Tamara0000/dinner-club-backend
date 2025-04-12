package com.dinnerclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DinnerClubBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DinnerClubBackendApplication.class, args);
    }

}
