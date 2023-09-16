package com.ecom.product_catelog.businesslayer;

import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link ProductDataService}.
 */
public class ProductDataService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static ProductDataService apply(RegisteredBean registeredBean,
      ProductDataService instance) {
    AutowiredFieldValueResolver.forRequiredField("productRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
