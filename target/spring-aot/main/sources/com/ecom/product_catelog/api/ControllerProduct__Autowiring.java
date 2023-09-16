package com.ecom.product_catelog.api;

import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link ControllerProduct}.
 */
public class ControllerProduct__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static ControllerProduct apply(RegisteredBean registeredBean, ControllerProduct instance) {
    instance.productDataService = AutowiredFieldValueResolver.forRequiredField("productDataService").resolve(registeredBean);
    return instance;
  }
}
