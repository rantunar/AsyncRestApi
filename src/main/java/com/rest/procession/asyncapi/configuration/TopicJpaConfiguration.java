package com.rest.procession.asyncapi.configuration;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.rest.procession.asyncapi.topic.repository"},
    entityManagerFactoryRef = "topicEntityManagerFactory",
    transactionManagerRef = "topicTransactionManager")
public class TopicJpaConfiguration {

  @Bean
  @ConfigurationProperties("spring.datasource.topics")
  public DataSourceProperties topicsDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "topicsDataSource")
  @Primary
  public DataSource topicsDataSource() {
    return topicsDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean topicEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(topicsDataSource())
        .packages("com.rest.procession.asyncapi.topic.entity")
        .build();
  }

  @Bean
  public PlatformTransactionManager topicTransactionManager(
      @Qualifier("topicEntityManagerFactory")
          LocalContainerEntityManagerFactoryBean topicEntityManagerFactory) {
    return new JpaTransactionManager(Objects.requireNonNull(topicEntityManagerFactory.getObject()));
  }
}
