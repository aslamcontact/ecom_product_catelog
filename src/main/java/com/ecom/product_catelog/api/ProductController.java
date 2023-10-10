package com.ecom.product_catelog.api;

import com.ecom.product_catelog.businesslayer.ProductDataService;
import com.ecom.product_catelog.businesslayer.ProductParser;
import com.ecom.product_catelog.daolayer.catelog.Product;
import com.ecom.product_catelog.daolayer.catelog.variation.VariationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    ProductDataService productDataService;
    @Autowired
    ProductParser parser;


    @PostMapping("/product/none")
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


    @PutMapping("/product/none")
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


     @PostMapping("/product/single")
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




    @PutMapping("/product/single")
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



    @PostMapping("/product/double")
    public ResponseEntity<Optional<ProductParser.ProductDouble>> addProductDouble(@RequestBody ProductParser.ProductDouble newProduct) {



        Optional<Product> result = productDataService
                .createProductWithDouble(newProduct.name(),
                        newProduct.brand(),
                        newProduct.descriptions(),
                        newProduct.about(),
                        newProduct.mainCategoryName(),
                        newProduct.categoriesWithSub()
                );

        return new ResponseEntity<>(
                Optional.of(parser.productToDouble(result)),
                HttpStatus.CREATED
        );

    }


    @PutMapping("/product/double")
    public ResponseEntity<ProductParser.ProductDouble> updateProductDouble(@RequestBody ProductParser.ProductDouble newProduct)
    {

        Optional<Product> result = productDataService
                .updateProductWithDouble( newProduct.name(),
                                          newProduct.brand(),
                                          newProduct.descriptions(),
                                          newProduct.about(),
                                          newProduct.mainCategoryName(),
                                          newProduct.categoriesWithSub()
                                        );



        return new ResponseEntity<>(parser.productToDouble(result),HttpStatus.CREATED);

    }

    @DeleteMapping("/product/{name}/{brand}")
    public ResponseEntity<String> removeProduct( @PathVariable(value = "name") String name,
                                                 @PathVariable(value = "brand") String brand)
    {

        productDataService.deleteProduct(name, brand);
        return new ResponseEntity<>("Product Deleted "+name+" Brand "+brand,
                HttpStatus.OK);

    }
    @GetMapping("/product/{name}/{brand}")
    public ResponseEntity<Optional<?>>  getProduct( @PathVariable(value = "name") String name,
                                                    @PathVariable(value = "brand") String brand)
    {

        Optional<Product> product;
        VariationType productType;
        product=productDataService.readProduct(name,brand);
        productType=product.get().getVariationType();
        if (productType.equals(VariationType.NONE))
            return new ResponseEntity<>(Optional.of(parser.productToNone(product)), HttpStatus.OK);
        else if(productType.equals(VariationType.DOUBLE))
            return new ResponseEntity<>(Optional.of(parser.productToDouble(product)), HttpStatus.OK);
        else
            return new ResponseEntity<>(Optional.of(parser.productToSingle(product)), HttpStatus.OK);
    }

    @GetMapping("/product/get/category/{name}/{brand}")
    public ResponseEntity<Optional<List<String>>> getCategories(   @PathVariable(value="name") String name,
                                                                   @PathVariable(value = "brand") String brand)
    {
              return new ResponseEntity<>(productDataService.filterCategories(name,brand), HttpStatus.OK);
    }



}
