package com.ecom.product_catelog.configuration;

import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link BeanConfiguration}.
 */
public class BeanConfiguration__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static BeanConfiguration apply(RegisteredBean registeredBean, BeanConfiguration instance) {
    AutowiredFieldValueResolver.forRequiredField("uri").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("database").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
