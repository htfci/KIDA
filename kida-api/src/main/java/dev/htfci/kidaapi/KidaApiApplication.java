package dev.htfci.kidaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class KidaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KidaApiApplication.class, args);
    }

}
