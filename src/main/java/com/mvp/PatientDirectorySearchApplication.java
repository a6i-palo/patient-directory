package com.mvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebSecurity
public class PatientDirectorySearchApplication {
  public static void main(String[] args) {
    SpringApplication.run(PatientDirectorySearchApplication.class, args);
  }
}
