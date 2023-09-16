package com.ecom.product_catelog;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.Override;
import java.util.List;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertySourceDescriptor;
import org.springframework.core.io.support.PropertySourceProcessor;

/**
 * {@link ApplicationContextInitializer} to restore an application context based on previous AOT processing.
 */
public class MainApplication__ApplicationContextInitializer implements ApplicationContextInitializer<GenericApplicationContext> {
  @Override
  public void initialize(GenericApplicationContext applicationContext) {
    DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();
    beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
    beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
    processPropertySources(applicationContext.getEnvironment(), applicationContext);
    new MainApplication__BeanFactoryRegistrations().registerBeanDefinitions(beanFactory);
    new MainApplication__BeanFactoryRegistrations().registerAliases(beanFactory);
  }

  /**
   * Apply known @PropertySources to the environment.
   */
  private void processPropertySources(ConfigurableEnvironment environment,
      ResourceLoader resourceLoader) {
    PropertySourceProcessor processor = new PropertySourceProcessor(environment, resourceLoader);
    try {
      processor.processPropertySource(new PropertySourceDescriptor(List.of("application.properties"), true, null, null, null));
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }
}
