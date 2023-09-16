package com.ecom.product_catelog.api;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ControllerProduct}.
 */
public class ControllerProduct__BeanDefinitions {
  /**
   * Get the bean definition for 'controllerProduct'.
   */
  public static BeanDefinition getControllerProductBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ControllerProduct.class);
    InstanceSupplier<ControllerProduct> instanceSupplier = InstanceSupplier.using(ControllerProduct::new);
    instanceSupplier = instanceSupplier.andThen(ControllerProduct__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
