package it.gov.pagopa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//import static it.gov.pagopa.UserRoleApplication.PACKAGE;

@SpringBootApplication
//@EnableMongoRepositories
//@ComponentScan(PACKAGE)
//@EnableJpaRepositories(PACKAGE + ".repository")
//@EntityScan(PACKAGE + ".model")
public class UserRoleApplication {

//  public static final String PACKAGE = "it.gov.pagopa";

  public static void main(String[] args) {
    SpringApplication.run(UserRoleApplication.class, args);
  }

}

