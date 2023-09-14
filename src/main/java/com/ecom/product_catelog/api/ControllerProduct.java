package com.ecom.product_catelog.api;

import com.ecom.product_catelog.businesslayer.ProductDataService;
import com.ecom.product_catelog.daolayer.catelog.Product;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/product")
    public ResponseEntity<ProductNone> addProduct(@RequestBody ProductNone newProduct) {



         Optional<Product> result = productDataService
                 .createProduct(newProduct.name(),
                         newProduct.brand(),
                         new ProductDataService.None(newProduct.quantity(),
                                 newProduct.price()),
                         newProduct.descriptions(),
                         newProduct.about());

         return new ResponseEntity<>(convertProduct(result), HttpStatus.CREATED);

    }
    @GetMapping("/product")
    public ResponseEntity<Optional<ProductNone>> getProduct( @RequestParam(value="name") String name,
                                                             @RequestParam(value = "brand") String brand)
    {
             Optional<Product> product;

            product=productDataService.readProduct(name,brand);
            return new ResponseEntity<>(Optional.of(convertProduct(product)), HttpStatus.OK);


    }
    @DeleteMapping("/product")
    public ResponseEntity<String> removeProduct( @RequestParam(value = "name") String name,
                                                      @RequestParam(value = "brand") String brand)
    {


                   productDataService.deleteProduct(name, brand);
                    return new ResponseEntity<>("Product Deleted "+name+" Brand "+brand,
                                                      HttpStatus.OK);

    }


    private ProductNone convertProduct(Optional<Product> product)
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

}
