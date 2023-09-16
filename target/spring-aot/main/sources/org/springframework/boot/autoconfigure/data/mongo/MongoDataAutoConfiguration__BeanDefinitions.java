package org.springframework.boot.autoconfigure.data.mongo;

import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.PropertiesMongoConnectionDetails;

/**
 * Bean definitions for {@link MongoDataAutoConfiguration}.
 */
public class MongoDataAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'mongoDataAutoConfiguration'.
   */
  public static BeanDefinition getMongoDataAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MongoDataAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(MongoDataAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'mongoConnectionDetails'.
   */
  private static BeanInstanceSupplier<PropertiesMongoConnectionDetails> getMongoConnectionDetailsInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<PropertiesMongoConnectionDetails>forFactoryMethod(MongoDataAutoConfiguration.class, "mongoConnectionDetails", MongoProperties.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(MongoDataAutoConfiguration.class).mongoConnectionDetails(args.get(0)));
  }

  /**
   * Get the bean definition for 'mongoConnectionDetails'.
   */
  public static BeanDefinition getMongoConnectionDetailsBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(PropertiesMongoConnectionDetails.class);
    beanDefinition.setInstanceSupplier(getMongoConnectionDetailsInstanceSupplier());
    return beanDefinition;
  }
}
