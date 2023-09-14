package com.ecom.product_catelog.daolayer.catelog.variation;

import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;

import java.util.Map;
import java.util.Optional;

public interface VariationV1<T> {
      Map<String, T> getVariations();
      String getName();
      VariationType getType();
      Optional<QuantityV1> getTotalQuantity();




}
