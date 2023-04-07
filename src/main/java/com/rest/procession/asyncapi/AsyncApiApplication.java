package com.rest.procession.asyncapi;

import com.rest.procession.asyncapi.configuration.UniqueNameGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.rest.procession.asyncapi"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AsyncApiApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .beanNameGenerator(new UniqueNameGenerator())
        .sources(AsyncApiApplication.class)
        .run();
  }
}
