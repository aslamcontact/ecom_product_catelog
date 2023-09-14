package com.ecom.product_catelog.daolayer.catelog.variation;

import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DoubleVariation implements VariationV1 {

    private String name;

    private  VariationType type;

    private Map<String, SingleVariation> variations;


    public DoubleVariation() {
        this.type=VariationType.DOUBLE;
        this.variations = new HashMap<>();
    }

    public  Map<String, SingleVariation> getVariations() {
        return variations;
    }

    public void setVariations(Map<String, SingleVariation> variations) {
        this.variations = variations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public VariationType getType() {
        return this.type;
    }

    @Override
    public Optional<QuantityV1> getTotalQuantity() {

        Comparator<QuantityV1> finMinPrice=(prevoius, current)->
        {
            return prevoius.getPrice().getPricePerItem()
                    .compareTo(current.getPrice().getPricePerItem());

        };
        return variations.values().stream()
                .flatMap(doubleVar->doubleVar.getVariations().values().stream())
                .filter(q -> q.getQuantity() > 0)
                .min(finMinPrice);
    }




}
