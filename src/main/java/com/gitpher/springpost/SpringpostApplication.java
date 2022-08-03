package com.gitpher.springpost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringpostApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringpostApplication.class, args);
    }

}
