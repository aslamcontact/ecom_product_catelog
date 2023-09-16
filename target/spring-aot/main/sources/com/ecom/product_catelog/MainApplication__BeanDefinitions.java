package com.ecom.product_catelog;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link MainApplication}.
 */
public class MainApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'mainApplication'.
   */
  public static BeanDefinition getMainApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MainApplication.class);
    beanDefinition.setTargetType(MainApplication.class);
    ConfigurationClassUtils.initializeConfigurationClass(MainApplication.class);
    beanDefinition.setInstanceSupplier(MainApplication$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
