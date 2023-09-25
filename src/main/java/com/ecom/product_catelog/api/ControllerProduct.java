package com.ecom.product_catelog.api;

import com.ecom.product_catelog.businesslayer.ProductDataService;
import com.ecom.product_catelog.daolayer.catelog.Pricing.Price;
import com.ecom.product_catelog.daolayer.catelog.Product;
import com.ecom.product_catelog.daolayer.catelog.quantity.NosQuantityV1;
import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;
import com.ecom.product_catelog.daolayer.catelog.variation.DoubleVariation;
import com.ecom.product_catelog.daolayer.catelog.variation.SingleVariation;
import com.ecom.product_catelog.daolayer.catelog.variation.VariationV1;
import com.mongodb.ClientSessionOptions;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
public class ControllerProduct {
    @Autowired
    ProductDataService productDataService;
    private record  ProductNone( @NonNull String name,
                                 @NonNull String brand,
                                 @NonNull List<String> descriptions,
                                 @NonNull Map<String,String> about,
                                 @NonNull Long quantity,
                                 @NonNull Double price
                               ){};
    private record ProductSingle( @NonNull String name,
                                     @NonNull String brand,
                                     @NonNull List<String> descriptions,
                                     @NonNull Map<String,String> about,
                                     @NonNull String categoryName,
                                     @NonNull List<ProductDataService.SingleDivision> categories


                                ){};
    private record ProductDouble( @NonNull String name,
                                  @NonNull String brand,
                                  @NonNull List<String> descriptions,
                                  @NonNull Map<String,String> about,
                                  @NonNull String categoryName,
                                  @NonNull List<ProductDataService.DoubleDivision> categoriesWithSub


                                ){};



    @PostMapping("/product/category/none")
    public ResponseEntity<Optional<ProductNone>> addProduct(@RequestBody ProductNone newProduct) {



         Optional<Product> result = productDataService
                 .createProductWithNone(newProduct.name(),
                         newProduct.brand(),
                         new ProductDataService.None(newProduct.quantity(),
                                 newProduct.price()),
                         newProduct.descriptions(),
                         newProduct.about());

         return new ResponseEntity<>(
                                      Optional.of(productToNone(result)),
                                      HttpStatus.CREATED
                                    );

    }
    @GetMapping("/product/category/none")
    public ResponseEntity<Optional<ProductNone>> getProduct( @RequestParam(value="name") String name,
                                                             @RequestParam(value = "brand") String brand)
    {
             Optional<Product> product;

            product=productDataService.readProduct(name,brand);
            return new ResponseEntity<>(Optional.of(productToNone(product)), HttpStatus.OK);


    }

    @PutMapping("/product/category/none")
    public ResponseEntity<Optional<ProductNone>> updateProduct(@RequestBody ProductNone newProduct)
    {
        Optional<Product> result = productDataService
                .updateProductWithNone(
                                        newProduct.name(),
                                        newProduct.brand(),
                                        new ProductDataService.None(newProduct.quantity(),
                                        newProduct.price()),
                                        newProduct.descriptions(),
                                        newProduct.about()
                                     );

        return new ResponseEntity<>(
                                      Optional.of(productToNone(result)),
                                      HttpStatus.CREATED
                                   );
    }


     @PostMapping("/product/category/single")
    public ResponseEntity<ProductSingle> addProductSingle(@RequestBody ProductSingle newProduct)
    {

        Optional<Product> result=productDataService.createProductWithSingle( newProduct.name(),
                                          newProduct.brand(),
                                          newProduct.descriptions(),
                                          newProduct.about(),
                                          newProduct.categories(),
                                          newProduct.categoryName());

        return new ResponseEntity<>(productToSingle(result),HttpStatus.CREATED);

    }

    @GetMapping("/product/category/single")
    public ResponseEntity<Optional<ProductSingle>> getProductSingle( @RequestParam(value="name") String name,
                                                                     @RequestParam(value = "brand") String brand)
    {
        Optional<Product> product;

        product=productDataService.readProduct(name,brand);
        return new ResponseEntity<>(Optional.of(productToSingle(product)), HttpStatus.OK);


    }

    @PutMapping("/product/category/single")
    public ResponseEntity<ProductSingle> updateProductSingle(@RequestBody ProductSingle newProduct)
    {

        Optional<Product> result=productDataService.updateProductWithSingle( newProduct.name(),
                newProduct.brand(),
                newProduct.descriptions(),
                newProduct.about(),
                newProduct.categories(),
                newProduct.categoryName());

        return new ResponseEntity<>(productToSingle(result),HttpStatus.CREATED);

    }



    @PostMapping("/product/category/double")
    public ResponseEntity<Optional<ProductDouble>> addProductDouble(@RequestBody ProductDouble newProduct) {



        Optional<Product> result = productDataService
                .createProductWithDouble(newProduct.name(),
                        newProduct.brand(),
                        newProduct.descriptions(),
                        newProduct.about(),
                        newProduct.categoryName(),
                        newProduct.categoriesWithSub()
                );

        return new ResponseEntity<>(
                Optional.of(productToDouble(result)),
                HttpStatus.CREATED
        );

    }


    @PutMapping("/product/category/double")
    public ResponseEntity<ProductDouble> updateProductDouble(@RequestBody ProductDouble newProduct)
    {

        Optional<Product> result = productDataService
                .updateProductWithDouble( newProduct.name(),
                                          newProduct.brand(),
                                          newProduct.descriptions(),
                                          newProduct.about(),
                                          newProduct.categoryName(),
                                          newProduct.categoriesWithSub()
                                        );



        return new ResponseEntity<>(productToDouble(result),HttpStatus.CREATED);

    }




    @DeleteMapping("/product")
    public ResponseEntity<String> removeProduct( @RequestParam(value = "name") String name,
                                                 @RequestParam(value = "brand") String brand)
    {


        productDataService.deleteProduct(name, brand);
        return new ResponseEntity<>("Product Deleted "+name+" Brand "+brand,
                HttpStatus.OK);

    }


    private ProductDouble productToDouble(Optional<Product> product) {
           Product result=product.get();

           //main Category Name eg:colors
         String mainCategoryName= result.getProductVariation().getName();
         //main Category value eg eg: blue,green
         Map<String,SingleVariation> mainCategories= (Map<String, SingleVariation>) result.getProductVariation().getVariations();
         List<ProductDataService.DoubleDivision> resultMainCategory=new ArrayList<>();
         //iterate main Category
         mainCategories.forEach(
                                  (mainCategoryValue,subCategory)->
                                   {

             List<ProductDataService.SubDiv> resultSubCategory = new ArrayList<>();

             //subCategory Name eg:size
             String subCategoryName=subCategory.getName();
             //subCategory Values eg:L,quantity(price,quantity)
             Map<String, QuantityV1> subCategoryValues = subCategory.getVariations();
             subCategoryValues.forEach(
                     (subCategoryValue, qtyprice) ->
                     {
                         resultSubCategory.add(
                                 new ProductDataService.SubDiv(
                                         subCategoryValue,
                                         qtyprice.getPrice().getPricePerItem(),
                                         qtyprice.getQuantity()
                                 )
                         );

                     }
             );
             resultMainCategory.add(
                     new ProductDataService.DoubleDivision(mainCategoryValue,subCategoryName,resultSubCategory)
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


    private ProductNone productToNone(Optional<Product> product)
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

    private ProductSingle productToSingle(Optional<Product> product)
    {
        Product result=product.get();
        List<ProductDataService.SingleDivision> categories=new ArrayList<>();

        result.getProductVariation()
                .getVariations()
                .forEach((type,qty)->
                        {
                            QuantityV1 qtyPrice= (QuantityV1) qty;

                            categories.add(new ProductDataService.SingleDivision( type,
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
