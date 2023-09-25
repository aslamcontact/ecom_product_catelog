package com.ecom.product_catelog.api;

import com.ecom.product_catelog.businesslayer.ProductDataService;
import com.ecom.product_catelog.daolayer.catelog.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    ProductDataService productDataService;
    @Autowired
    ProductParser parser;


    @PostMapping("/product/category/none")
    public ResponseEntity<Optional<ProductParser.ProductNone>> addProduct(@RequestBody ProductParser.ProductNone newProduct) {



         Optional<Product> result = productDataService
                 .createProductWithNone(newProduct.name(),
                         newProduct.brand(),
                         new ProductDataService.None(newProduct.quantity(),
                                 newProduct.price()),
                         newProduct.descriptions(),
                         newProduct.about());

         return new ResponseEntity<>(
                                      Optional.of(parser.productToNone(result)),
                                      HttpStatus.CREATED
                                    );

    }
    @GetMapping("/product/category/none")
    public ResponseEntity<Optional<ProductParser.ProductNone>> getProduct(@RequestParam(value="name") String name,
                                                                          @RequestParam(value = "brand") String brand)
    {
             Optional<Product> product;

            product=productDataService.readProduct(name,brand);
            return new ResponseEntity<>(Optional.of(parser.productToNone(product)), HttpStatus.OK);


    }

    @PutMapping("/product/category/none")
    public ResponseEntity<Optional<ProductParser.ProductNone>> updateProduct(@RequestBody ProductParser.ProductNone newProduct)
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
                                      Optional.of(parser.productToNone(result)),
                                      HttpStatus.CREATED
                                   );
    }


     @PostMapping("/product/category/single")
    public ResponseEntity<ProductParser.ProductSingle> addProductSingle(@RequestBody ProductParser.ProductSingle newProduct)
    {

        Optional<Product> result=productDataService.createProductWithSingle( newProduct.name(),
                                          newProduct.brand(),
                                          newProduct.descriptions(),
                                          newProduct.about(),
                                          newProduct.categories(),
                                          newProduct.categoryName());

        return new ResponseEntity<>(parser.productToSingle(result),HttpStatus.CREATED);

    }

    @GetMapping("/product/category/single")
    public ResponseEntity<Optional<ProductParser.ProductSingle>> getProductSingle( @RequestParam(value="name") String name,
                                                                     @RequestParam(value = "brand") String brand)
    {
        Optional<Product> product;

        product=productDataService.readProduct(name,brand);
        return new ResponseEntity<>(Optional.of(parser.productToSingle(product)), HttpStatus.OK);


    }

    @PutMapping("/product/category/single")
    public ResponseEntity<ProductParser.ProductSingle> updateProductSingle(@RequestBody ProductParser.ProductSingle newProduct)
    {

        Optional<Product> result=productDataService.updateProductWithSingle( newProduct.name(),
                newProduct.brand(),
                newProduct.descriptions(),
                newProduct.about(),
                newProduct.categories(),
                newProduct.categoryName());

        return new ResponseEntity<>(parser.productToSingle(result),HttpStatus.CREATED);

    }



    @PostMapping("/product/category/double")
    public ResponseEntity<Optional<ProductParser.ProductDouble>> addProductDouble(@RequestBody ProductParser.ProductDouble newProduct) {



        Optional<Product> result = productDataService
                .createProductWithDouble(newProduct.name(),
                        newProduct.brand(),
                        newProduct.descriptions(),
                        newProduct.about(),
                        newProduct.categoryName(),
                        newProduct.categoriesWithSub()
                );

        return new ResponseEntity<>(
                Optional.of(parser.productToDouble(result)),
                HttpStatus.CREATED
        );

    }


    @PutMapping("/product/category/double")
    public ResponseEntity<ProductParser.ProductDouble> updateProductDouble(@RequestBody ProductParser.ProductDouble newProduct)
    {

        Optional<Product> result = productDataService
                .updateProductWithDouble( newProduct.name(),
                                          newProduct.brand(),
                                          newProduct.descriptions(),
                                          newProduct.about(),
                                          newProduct.categoryName(),
                                          newProduct.categoriesWithSub()
                                        );



        return new ResponseEntity<>(parser.productToDouble(result),HttpStatus.CREATED);

    }




    @DeleteMapping("/product")
    public ResponseEntity<String> removeProduct( @RequestParam(value = "name") String name,
                                                 @RequestParam(value = "brand") String brand)
    {


        productDataService.deleteProduct(name, brand);
        return new ResponseEntity<>("Product Deleted "+name+" Brand "+brand,
                HttpStatus.OK);

    }



}
