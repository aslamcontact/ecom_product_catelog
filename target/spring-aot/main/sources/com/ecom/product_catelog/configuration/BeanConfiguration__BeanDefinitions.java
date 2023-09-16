package com.ecom.product_catelog.configuration;

import com.ecom.product_catelog.daolayer.catelog.Pricing.Price;
import com.ecom.product_catelog.daolayer.catelog.Pricing.PriceV1;
import com.ecom.product_catelog.daolayer.catelog.Product;
import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;
import com.ecom.product_catelog.daolayer.catelog.variation.DoubleVariation;
import com.ecom.product_catelog.daolayer.catelog.variation.SingleVariation;
import com.ecom.product_catelog.daolayer.catelog.variation.VariationV1;
import com.mongodb.client.MongoClient;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Bean definitions for {@link BeanConfiguration}.
 */
public class BeanConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'beanConfiguration'.
   */
  public static BeanDefinition getBeanConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(BeanConfiguration.class);
    beanDefinition.setTargetType(BeanConfiguration.class);
    ConfigurationClassUtils.initializeConfigurationClass(BeanConfiguration.class);
    InstanceSupplier<BeanConfiguration> instanceSupplier = InstanceSupplier.using(BeanConfiguration$$SpringCGLIB$$0::new);
    instanceSupplier = instanceSupplier.andThen(BeanConfiguration__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'mongoTemplate'.
   */
  private static BeanInstanceSupplier<MongoTemplate> getMongoTemplateInstanceSupplier() {
    return BeanInstanceSupplier.<MongoTemplate>forFactoryMethod(BeanConfiguration.class, "mongoTemplate")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(BeanConfiguration.class).mongoTemplate());
  }

  /**
   * Get the bean definition for 'mongoTemplate'.
   */
  public static BeanDefinition getMongoTemplateBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(MongoTemplate.class);
    beanDefinition.setInstanceSupplier(getMongoTemplateInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'mongoClient'.
   */
  private static BeanInstanceSupplier<MongoClient> getMongoClientInstanceSupplier() {
    return BeanInstanceSupplier.<MongoClient>forFactoryMethod(BeanConfiguration.class, "mongoClient")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(BeanConfiguration.class).mongoClient());
  }

  /**
   * Get the bean definition for 'mongoClient'.
   */
  public static BeanDefinition getMongoClientBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(MongoClient.class);
    beanDefinition.setDestroyMethodNames("close");
    beanDefinition.setInstanceSupplier(getMongoClientInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'price'.
   */
  private static BeanInstanceSupplier<Price> getPriceInstanceSupplier() {
    return BeanInstanceSupplier.<Price>forFactoryMethod(BeanConfiguration.class, "price", Double.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(BeanConfiguration.class).price(args.get(0)));
  }

  /**
   * Get the bean definition for 'price'.
   */
  public static BeanDefinition getPriceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(Price.class);
    beanDefinition.setScope("prototype");
    beanDefinition.setInstanceSupplier(getPriceInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'nos-quantity'.
   */
  private static BeanInstanceSupplier<QuantityV1> getNosquantityInstanceSupplier() {
    return BeanInstanceSupplier.<QuantityV1>forFactoryMethod(BeanConfiguration.class, "nosQuantity", Long.class, PriceV1.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(BeanConfiguration.class).nosQuantity(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'nos-quantity'.
   */
  public static BeanDefinition getNosquantityBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(QuantityV1.class);
    beanDefinition.setScope("prototype");
    beanDefinition.setInstanceSupplier(getNosquantityInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'single-variation'.
   */
  private static BeanInstanceSupplier<SingleVariation> getSinglevariationInstanceSupplier() {
    return BeanInstanceSupplier.<SingleVariation>forFactoryMethod(BeanConfiguration.class, "singleVariation", String.class, Map.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(BeanConfiguration.class).singleVariation(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'single-variation'.
   */
  public static BeanDefinition getSinglevariationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(SingleVariation.class);
    beanDefinition.setScope("prototype");
    beanDefinition.setInstanceSupplier(getSinglevariationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'double-variation'.
   */
  private static BeanInstanceSupplier<DoubleVariation> getDoublevariationInstanceSupplier() {
    return BeanInstanceSupplier.<DoubleVariation>forFactoryMethod(BeanConfiguration.class, "doubleVariation", String.class, Map.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(BeanConfiguration.class).doubleVariation(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'double-variation'.
   */
  public static BeanDefinition getDoublevariationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(DoubleVariation.class);
    beanDefinition.setScope("prototype");
    beanDefinition.setInstanceSupplier(getDoublevariationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'product-none'.
   */
  private static BeanInstanceSupplier<Product> getProductnoneInstanceSupplier() {
    return BeanInstanceSupplier.<Product>forFactoryMethod(BeanConfiguration.class, "productNone", String.class, String.class, Map.class, List.class, QuantityV1.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(BeanConfiguration.class).productNone(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'product-none'.
   */
  public static BeanDefinition getProductnoneBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(Product.class);
    beanDefinition.setScope("prototype");
    beanDefinition.setInstanceSupplier(getProductnoneInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'product-single'.
   */
  private static BeanInstanceSupplier<Product> getProductsingleInstanceSupplier() {
    return BeanInstanceSupplier.<Product>forFactoryMethod(BeanConfiguration.class, "productSingle", String.class, String.class, Map.class, List.class, VariationV1.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(BeanConfiguration.class).productSingle(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'product-single'.
   */
  public static BeanDefinition getProductsingleBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition();
    beanDefinition.setTargetType(Product.class);
    beanDefinition.setScope("prototype");
    beanDefinition.setInstanceSupplier(getProductsingleInstanceSupplier());
    return beanDefinition;
  }
}
