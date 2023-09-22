package com.ecom.product_catelog.api;

import com.ecom.product_catelog.businesslayer.ProductDataService;
import com.ecom.product_catelog.daolayer.catelog.Pricing.Price;
import com.ecom.product_catelog.daolayer.catelog.Product;
import com.ecom.product_catelog.daolayer.catelog.quantity.NosQuantityV1;
import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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



    @PostMapping("/product/category/none")
    public ResponseEntity<ProductNone> addProduct(@RequestBody ProductNone newProduct) {



         Optional<Product> result = productDataService
                 .createProduct(newProduct.name(),
                         newProduct.brand(),
                         new ProductDataService.None(newProduct.quantity(),
                                 newProduct.price()),
                         newProduct.descriptions(),
                         newProduct.about());

         return new ResponseEntity<>(productToNone(result), HttpStatus.CREATED);

    }
    @GetMapping("/product/category/none")
    public ResponseEntity<Optional<ProductNone>> getProduct( @RequestParam(value="name") String name,
                                                             @RequestParam(value = "brand") String brand)
    {
             Optional<Product> product;

            product=productDataService.readProduct(name,brand);
            return new ResponseEntity<>(Optional.of(productToNone(product)), HttpStatus.OK);


    }
    @DeleteMapping("/product")
    public ResponseEntity<String> removeProduct( @RequestParam(value = "name") String name,
                                                      @RequestParam(value = "brand") String brand)
    {


                   productDataService.deleteProduct(name, brand);
                    return new ResponseEntity<>("Product Deleted "+name+" Brand "+brand,
                                                      HttpStatus.OK);

    }
    @PutMapping("/product/category/none")
    public ResponseEntity<Optional<ProductNone>> updateProduct(@RequestBody ProductNone newProduct)
    {
        Product findProduct= productDataService
                                      .readProduct( newProduct.name(),
                                                    newProduct.brand()).get();
           findProduct.setAbout(newProduct.about());
           findProduct.setProductDescription(newProduct.descriptions());
           findProduct.setQuantityAndPrice(new NosQuantityV1( newProduct.quantity(),
                                                              new Price(newProduct.price()))
                                          );

          Optional<Product> result=Optional.of(productDataService
                  .updateProduct(findProduct));

        return new ResponseEntity<>(Optional.of(productToNone(result)),
                                     HttpStatus.OK);
    }


     @PostMapping("/product/category/single")
    public ResponseEntity<ProductSingle> addProductSingle(@RequestBody ProductSingle newProduct)
    {

        Optional<Product> result=productDataService.createProduct( newProduct.name(),
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
        List< ProductDataService.SingleDivision> categories=new ArrayList<>();

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
