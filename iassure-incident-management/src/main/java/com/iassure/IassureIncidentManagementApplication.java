package com.iassure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IassureIncidentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(IassureIncidentManagementApplication.class, args);
    }

}
