package com.ecom.product_catelog.businesslayer;

import com.ecom.product_catelog.configuration.BeanConfiguration;
import com.ecom.product_catelog.daolayer.catelog.*;
import com.ecom.product_catelog.daolayer.catelog.Product;
import com.ecom.product_catelog.daolayer.catelog.ProductRepository;
import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;
import com.ecom.product_catelog.daolayer.catelog.variation.*;
import com.ecom.product_catelog.exceptions.ProductException;
import com.ecom.product_catelog.daolayer.catelog.variation.SingleVariation;
import com.ecom.product_catelog.daolayer.catelog.variation.VariationV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductDataService {
    @Autowired
    private ProductRepository productRepository;

    AnnotationConfigApplicationContext beans= new AnnotationConfigApplicationContext(BeanConfiguration.class);

    private final String qtyBean="nos-quantity";
    private final String priceBean="price";
    private final String doubleDivBean = "double-variation";
    private final String singleDivBean="single-variation";

    private final String productNoneBean="product-none";
    private final String productVarBean="product-single";

    final


    public  record  DoubleDivision(String mainDivName,String subDivName,List<SubDiv> subDivs){};
    public  record  SubDiv(String divideValue,Double price,Long quantity){};
    public  record  SingleDivision(String divideValue, Double price, Long quantity){};
    public  record  None(Long quantity,Double price){};



//create product with double categories

    public Optional<Product> createProduct(String productName,
                                           String productBrand,
                                           List<String> descriptions,
                                           Map<String,String> aboutProduct,
                                           String divisionName,
                                           List<DoubleDivision> divideProducts

    )
    {
        VariationV1<?> variation;
        Product newProduct;
        productBrand=productBrand.trim().toLowerCase();
        productName=productName.trim().toLowerCase();

        if(checkProduct(productName,productBrand))
            throw new ProductException("Product Name "+productName+
                    " and Brand "+productBrand+" is Already Exists ");


        Map<String, SingleVariation> divResult=divideProducts
                .stream()
                .collect(Collectors.toMap(
                                          DoubleDivision::mainDivName,
                                          (nextLevel)-> (SingleVariation) beans.getBean(singleDivBean,
                                                               nextLevel.subDivName(),
                                                               nextLevel.subDivs().stream()
                                                               .collect(Collectors.toMap(SubDiv::divideValue,
                                                                       q->beans.getBean(  qtyBean,
                                                                                           q.quantity(),
                                                                                           beans.getBean(priceBean,
                                                                                                          q.price()))))
                                                  )


                        )


                  );

         variation = (VariationV1<?>) beans.getBean(doubleDivBean,divisionName,divResult);


         newProduct= (Product) beans.getBean( productVarBean,
                                              productName,
                                              productBrand,
                                              aboutProduct,
                                              descriptions,
                                               variation);


        return Optional.of(productRepository.save(newProduct));

    }



    //create product with categories

    public Optional<Product> createProduct(     String productName,
                                                String productBrand,
                                                List<String> descriptions,
                                                Map<String,String> aboutProduct,
                                                List<SingleDivision> divideProducts,
                                                String divisionName
                                            )
    {

        VariationV1<?> variation;
        Product newProduct;


        productBrand=productBrand.trim().toLowerCase();
        productName=productName.trim().toLowerCase();
        if(checkProduct(productName,productBrand))
            throw new ProductException("Product Name "+productName+
                                       " and Brand "+productBrand+" is Already Exists ");

        Map<String, QuantityV1> divResult=divideProducts
                                            .stream()
                                            .collect( Collectors.toMap(
                                                      SingleDivision::divideValue,
                                                      subQuantity->
                                                       (QuantityV1) beans.getBean(  qtyBean,
                                                                                     subQuantity.quantity(),
                                                                                     beans.getBean( priceBean,
                                                                                                     subQuantity.price()
                                                                                                  )
                                                                                  )
                                                                     )
                                                   );


          variation= (VariationV1<?>) beans.getBean(singleDivBean,divisionName,divResult);


         newProduct= (Product) beans.getBean(   productVarBean,
                                                productName,
                                                productBrand,
                                                aboutProduct,
                                                descriptions,
                                                variation);


        return Optional.of(productRepository.save(newProduct));

    }


    public Optional<Product> updateProductWithSingle(
                                                        String productName,
                                                        String productBrand,
                                                        List<String> descriptions,
                                                        Map<String,String> aboutProduct,
                                                        List<SingleDivision> divideProducts,
                                                        String divisionName
                                                    )
    {

        VariationV1<?> variation;
        Product newProduct;


        productBrand=productBrand.trim().toLowerCase();
        productName=productName.trim().toLowerCase();
        if(!checkProduct(productName,productBrand))
            throw new ProductException("Product Name "+productName+
                    " and Brand "+productBrand+" there is no product ");

        Map<String, QuantityV1> divResult=divideProducts
                .stream()
                .collect( Collectors.toMap(
                                SingleDivision::divideValue,
                                subQuantity->
                                        (QuantityV1) beans.getBean(  qtyBean,
                                                subQuantity.quantity(),
                                                beans.getBean( priceBean,
                                                        subQuantity.price()
                                                )
                                        )
                        )
                );


        variation= (VariationV1<?>) beans.getBean(singleDivBean,divisionName,divResult);


        newProduct= (Product) beans.getBean(
                                              productVarBean,
                                              productName,
                                              productBrand,
                                              aboutProduct,
                                              descriptions,
                                               variation
                                           );


        return Optional.of(productRepository.save(newProduct));

    }

    //create Product with Non Caregories

    public Optional<Product> createProductWithNone( String productName,
                                            String productBrand,
                                            None priceQuantity,
                                            List<String> descriptions,
                                            Map<String,String> aboutProduct)
    {

                        Product newProduct;
                  productBrand=productBrand.trim().toLowerCase();
                  productName=productName.trim().toLowerCase();

                  if(checkProduct(productName,productBrand))
                      throw new ProductException("Product Name "+productName+
                                                   "  and Brand"+productBrand+
                                                     " is  Already Used");

                  QuantityV1 quantity= (QuantityV1) beans.getBean(qtyBean,
                                                             priceQuantity.quantity(),
                                                             beans.getBean(priceBean,priceQuantity.price())
                                                         );
                  newProduct= (Product) beans.getBean(  productNoneBean,
                                                        productName,
                                                        productBrand,
                                                        aboutProduct,
                                                        descriptions,
                                                        quantity);

                   return Optional.of(productRepository.save(newProduct));

    }

    //update product without category
    public Optional<Product> updateProductWithNone(
            String productName,
            String productBrand,
            None priceQuantity,
            List<String> descriptions,
            Map<String,String> aboutProduct
    )
    {

        Product newProduct;
        productBrand=productBrand.trim().toLowerCase();
        productName=productName.trim().toLowerCase();

        if(!checkProduct(productName,productBrand))
            throw new ProductException("There is no product with this productName and productBrand");

        QuantityV1 quantity= (QuantityV1) beans.getBean(qtyBean,
                priceQuantity.quantity(),
                beans.getBean(priceBean,priceQuantity.price())
        );
        newProduct= (Product) beans.getBean(  productNoneBean,
                productName,
                productBrand,
                aboutProduct,
                descriptions,
                quantity);

        return Optional.of(productRepository.save(newProduct));
    }

    public Optional<Product> readProduct(String productName,String productBrand)
    {
        Optional<Product> result;
        result= productRepository
                .findById("Id_"+(productName+productBrand.toLowerCase().trim()));
        if(result.isEmpty())
            throw new ProductException("There is No Product With This Name "
                    +productName+" and Brand "+productBrand);
        return result;
    }



    //Delete product

    public void deleteProduct(String productName,String productBrand)
    {
        productName=productName.trim().toLowerCase();
        productBrand=productBrand.trim().toLowerCase();

        if(!checkProduct(productName,productBrand))
            throw new ProductException("There is No Product with This Name and Product ( "
                    +productName+" "+productBrand+" )");

        productRepository.deleteById("Id_"+productName+productBrand);
    }




    //private methods


    private Boolean checkProduct(String productName,String brand)
    {

        return productRepository.findById("Id_"+productName+brand).isPresent();
    }


}