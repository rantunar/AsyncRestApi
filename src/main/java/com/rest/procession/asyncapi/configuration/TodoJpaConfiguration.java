package com.rest.procession.asyncapi.configuration;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.rest.procession.asyncapi.repository"},
    entityManagerFactoryRef = "todosEntityManagerFactory",
    transactionManagerRef = "todosTransactionManager")
public class TodoJpaConfiguration {

  @Bean
  @ConfigurationProperties("spring.datasource.todos")
  public DataSourceProperties todosDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "todosDataSource")
  public DataSource todosDataSource() {
    return todosDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean todosEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(todosDataSource())
        .packages("com.rest.procession.asyncapi.entity")
        .build();
  }

  @Bean
  public PlatformTransactionManager todosTransactionManager(
      @Qualifier("todosEntityManagerFactory")
          LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
    return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
  }
}
