package com.ecom.product_catelog.exceptions;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ProductExceptionHandler}.
 */
public class ProductExceptionHandler__BeanDefinitions {
  /**
   * Get the bean definition for 'productExceptionHandler'.
   */
  public static BeanDefinition getProductExceptionHandlerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ProductExceptionHandler.class);
    beanDefinition.setInstanceSupplier(ProductExceptionHandler::new);
    return beanDefinition;
  }
}
