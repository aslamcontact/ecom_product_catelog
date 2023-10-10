package com.ecom.product_catelog.businesslayer;

import com.ecom.product_catelog.businesslayer.ProductDataService;
import com.ecom.product_catelog.daolayer.catelog.Product;
import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;
import com.ecom.product_catelog.daolayer.catelog.variation.SingleVariation;
import com.mongodb.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("product_parser")
public class ProductParser {

    public record  ProductNone( @NonNull String name,
                                 @NonNull String brand,
                                 @NonNull List<String> descriptions,
                                 @NonNull Map<String,String> about,
                                 @NonNull Long quantity,
                                 @NonNull Double price
    ){};
     public record ProductSingle( @NonNull String name,
                                  @NonNull String brand,
                                  @NonNull List<String> descriptions,
                                  @NonNull Map<String,String> about,
                                  @NonNull String categoryName,
                                  @NonNull List<ProductDataService.SingleCategory> categories


    ){};
    public record ProductDouble( @NonNull String name,
                                  @NonNull String brand,
                                  @NonNull List<String> descriptions,
                                  @NonNull Map<String,String> about,
                                  @NonNull String mainCategoryName,
                                  @NonNull List<ProductDataService.DoubleCategory> categoriesWithSub


    ){};



    public ProductDouble productToDouble(Optional<Product> product) {
        Product result=product.get();

        //main Category Name eg:colors
        String mainCategoryName= result.getProductVariation().getName();
        //main Category value eg eg: blue,green
        Map<String, SingleVariation> mainCategories= (Map<String, SingleVariation>) result.getProductVariation().getVariations();
        List<ProductDataService.DoubleCategory> resultMainCategory=new ArrayList<>();
        //iterate main Category
        mainCategories.forEach(
                (mainCategoryValue,subCategory)->
                {

                    List<ProductDataService.SubCategory> resultSubCategory = new ArrayList<>();

                    //subCategory Name eg:size
                    String subCategoryName=subCategory.getName();
                    //subCategory Values eg:L,quantity(price,quantity)
                    Map<String, QuantityV1> subCategoryValues = subCategory.getVariations();
                    subCategoryValues.forEach(
                            (subCategoryValue, qtyprice) ->
                            {
                                resultSubCategory.add(
                                        new ProductDataService.SubCategory(
                                                subCategoryValue,
                                                qtyprice.getPrice().getPricePerItem(),
                                                qtyprice.getQuantity()
                                        )
                                );

                            }
                    );
                    resultMainCategory.add(
                            new ProductDataService.DoubleCategory(mainCategoryValue,subCategoryName,resultSubCategory)
                    );

                }
        );


        return new ProductDouble(
                result.getProductName(),
                result.getBrand(),
                result.getProductDescription(),
                result.getAbout(),
                result.getProductVariation().getName(),
                resultMainCategory
        );

    }


    public ProductNone productToNone(Optional<Product> product)
    {
        Product result=product.get();

        return  new ProductNone( result.getProductName(),
                result.getBrand(),
                result.getProductDescription(),
                result.getAbout(),
                result.getQuantityAndPrice().get()
                        .getQuantity(),
                result.getQuantityAndPrice().get()
                        .getPrice()
                        .getPricePerItem());

    }

    public ProductSingle productToSingle(Optional<Product> product)
    {
        Product result=product.get();
        List<ProductDataService.SingleCategory> categories=new ArrayList<>();

        result.getProductVariation()
                .getVariations()
                .forEach((type,qty)->
                        {
                            QuantityV1 qtyPrice= (QuantityV1) qty;

                            categories.add(new ProductDataService.SingleCategory(type,
                                    qtyPrice.getPrice().getPricePerItem(),
                                    qtyPrice.getQuantity()));
                        }

                );

        return new ProductSingle( result.getProductName(),
                result.getBrand(),
                result.getProductDescription(),
                result.getAbout(),
                result.getProductVariation().getName(),
                categories
        );
    }



}
