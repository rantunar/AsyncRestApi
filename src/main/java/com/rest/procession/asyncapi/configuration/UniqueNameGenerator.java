package com.rest.procession.asyncapi.configuration;

import java.beans.Introspector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

@Slf4j
public class UniqueNameGenerator implements BeanNameGenerator {
  @Override
  public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
    String beanClassName = Introspector.decapitalize(definition.getBeanClassName());
    log.info("Instantiating bean with name: " + beanClassName);
    return beanClassName;
  }
}
