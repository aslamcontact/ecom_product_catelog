package com.ecom.product_catelog.businesslayer;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ProductDataService}.
 */
public class ProductDataService__BeanDefinitions {
  /**
   * Get the bean definition for 'productDataService'.
   */
  public static BeanDefinition getProductDataServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ProductDataService.class);
    InstanceSupplier<ProductDataService> instanceSupplier = InstanceSupplier.using(ProductDataService::new);
    instanceSupplier = instanceSupplier.andThen(ProductDataService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
