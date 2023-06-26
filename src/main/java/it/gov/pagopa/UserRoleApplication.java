package it.gov.pagopa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "it.gov.pagopa")
public class UserRoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRoleApplication.class, args);
    }

}

