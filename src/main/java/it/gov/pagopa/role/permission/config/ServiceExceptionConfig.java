package it.gov.pagopa.role.permission.config;

import it.gov.pagopa.common.web.exception.ServiceException;
import it.gov.pagopa.role.permission.exception.PermissionNotFoundException;
import it.gov.pagopa.role.permission.exception.VersionNotMatchedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServiceExceptionConfig {

  @Bean
  public Map<Class<? extends ServiceException>, HttpStatus> serviceExceptionMapper() {
    Map<Class<? extends ServiceException>, HttpStatus> exceptionMap = new HashMap<>();

    // NotFound
    exceptionMap.put(PermissionNotFoundException.class, HttpStatus.NOT_FOUND);

    //BadRequest
    exceptionMap.put(VersionNotMatchedException.class, HttpStatus.BAD_REQUEST);

    return exceptionMap;
  }

}
