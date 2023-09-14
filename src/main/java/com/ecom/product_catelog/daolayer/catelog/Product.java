package com.ecom.product_catelog.daolayer.catelog;

import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;
import com.ecom.product_catelog.daolayer.catelog.variation.VariationType;
import com.ecom.product_catelog.daolayer.catelog.variation.VariationV1;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class Product {

    //id Start witth Id_ and combine productName and brand
    @Id
    private String productId;

    private String productName;
    private String brand;
   private List<String> productDescription;

   private Map<String,String> about;

   private VariationV1<?> variation;

   private VariationType variationType;

   private QuantityV1 quantityAndPrice;

    public Product(  String productName,
                     String brand,
                     Map<String,String> about,
                     List<String> productDescription
                   ) {
        this.productName = productName.toLowerCase();
        this.variationType=VariationType.NONE;
        this.brand=brand.toLowerCase();
        this.productId="Id_"+(productName+brand.trim().toLowerCase());
        this.about=about;
        this.productDescription=productDescription;

    }




    public Optional<QuantityV1> getQuantityAndPrice() {
        
         QuantityV1 resultVar=null;

        switch (variationType) {
            case NONE -> {
                resultVar= quantityAndPrice;
            }
            case SINGLE, DOUBLE -> {
                resultVar= variation.getTotalQuantity().get();
            }
        }
        return Optional.ofNullable(resultVar);
    }

    public void setQuantityAndPrice(QuantityV1 quantityAndPrice) {


          if(this.variationType.equals(VariationType.NONE))
             this.quantityAndPrice = quantityAndPrice;
          else throw new RuntimeException("Variation is Not None Type");

    }


    public void setVariation(VariationV1<?> variation) {
        this.setVariationType(variation.getType());
        this.variation = variation;
    }

    public VariationType getVariationType() {
        return variationType;
    }
    public VariationV1<?> getProductVariation() {
        return variation;
    }

    public void setVariationType(VariationType variationType) {

        this.variationType = variationType;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName.toLowerCase();
    }

    public List<String> getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(List<String> productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductID() {
        return productId;
    }

    public void setProductID(String productId) {
        this.productId = "Id_"+productId.toLowerCase();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand.toLowerCase();
    }

    public Map<String, String> getAbout() {
        return about;
    }

    public void setAbout(Map<String, String> about) {
        this.about = about;
    }
}
