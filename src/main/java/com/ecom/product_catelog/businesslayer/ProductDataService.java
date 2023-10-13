package com.ecom.product_catelog.businesslayer;

import com.ecom.product_catelog.configuration.BeanConfiguration;
import com.ecom.product_catelog.daolayer.catelog.Product;
import com.ecom.product_catelog.daolayer.catelog.ProductRepository;
import com.ecom.product_catelog.daolayer.catelog.quantity.QuantityV1;
import com.ecom.product_catelog.daolayer.catelog.variation.SingleVariation;
import com.ecom.product_catelog.daolayer.catelog.variation.VariationType;
import com.ecom.product_catelog.daolayer.catelog.variation.VariationV1;
import com.ecom.product_catelog.exceptions.images.ImageMapperExistException;
import com.ecom.product_catelog.exceptions.images.ProductCategoryNotExist;
import com.ecom.product_catelog.exceptions.product.ProductExistException;
import com.ecom.product_catelog.exceptions.product.ProductNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductDataService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  ProductParser parser;

    @Autowired
    private ImageServiceExternal imageServiceExternal;
    AnnotationConfigApplicationContext beans= new AnnotationConfigApplicationContext(BeanConfiguration.class);

    private final String qtyBean="nos-quantity";
    private final String priceBean="price";
    private final String doubleDivBean = "double-variation";
    private final String singleDivBean="single-variation";

    private final String productNoneBean="product-none";
    private final String productVarBean="product-single";

    final


    public  record  DoubleCategory(String mainCategories,String subCategoryName,List<SubCategory> subCategory){};
    public  record  SubCategory(String subCategories,Double price,Long quantity){};
    public  record  SingleCategory(String value, Double price, Long quantity){};
    public  record  None(Long quantity,Double price){};



//create product with double categories

    public Optional<Product> createProductWithDouble(String productName,
                                           String productBrand,
                                           List<String> descriptions,
                                           Map<String,String> aboutProduct,
                                           String divisionName,
                                           List<DoubleCategory> divideProducts

    )
    {
        VariationV1<?> variation;
        Product newProduct;
        productBrand=productBrand.trim().toLowerCase();
        productName=productName.trim().toLowerCase();

        if(checkProduct(productName,productBrand))
            throw new ProductExistException(productName,productBrand);


        Map<String, SingleVariation> divResult=divideProducts
                .stream()
                .collect(Collectors.toMap(
                                          DoubleCategory::mainCategories,
                                          (nextLevel)-> (SingleVariation) beans.getBean(singleDivBean,
                                                               nextLevel.subCategoryName(),
                                                               nextLevel.subCategory().stream()
                                                               .collect(Collectors.toMap(SubCategory::subCategories,
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

        try {
            imageServiceExternal.saveImageMapper(newProduct.getProductID());
        }catch (ImageMapperExistException exist)
        {

            throw new ProductExistException(productName,productBrand);
        }


        return Optional.of(productRepository.save(newProduct));

    }


    public Optional<Product> updateProductWithDouble(String productName,
                                                     String productBrand,
                                                     List<String> descriptions,
                                                     Map<String,String> aboutProduct,
                                                     String divisionName,
                                                     List<DoubleCategory> divideProducts

    )
    {
        VariationV1<?> variation;
        Product newProduct;
        productBrand=productBrand.trim().toLowerCase();
        productName=productName.trim().toLowerCase();

        if(!checkProduct(productName,productBrand))
            throw new ProductNotExistException(productName,productBrand);


        Map<String, SingleVariation> divResult=divideProducts
                .stream()
                .collect(Collectors.toMap(
                                DoubleCategory::mainCategories,
                                (nextLevel)-> (SingleVariation) beans.getBean(singleDivBean,
                                        nextLevel.subCategoryName(),
                                        nextLevel.subCategory().stream()
                                                .collect(Collectors.toMap(SubCategory::subCategories,
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

    public Optional<Product> createProductWithSingle(     String productName,
                                                String productBrand,
                                                List<String> descriptions,
                                                Map<String,String> aboutProduct,
                                                List<SingleCategory> divideProducts,
                                                String divisionName
                                            )
    {

        VariationV1<?> variation;
        Product newProduct;


        productBrand=productBrand.trim().toLowerCase();
        productName=productName.trim().toLowerCase();
        if(checkProduct(productName,productBrand))
            throw new ProductExistException(productName,productBrand);

        Map<String, QuantityV1> divResult=divideProducts
                                            .stream()
                                            .collect( Collectors.toMap(
                                                      SingleCategory::value,
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

        try {
            imageServiceExternal.saveImageMapper(newProduct.getProductID());
            System.out.println(newProduct.getProductID());
        }catch (ImageMapperExistException exist)
        {

            throw new ProductExistException(productName,productBrand);
        }


        return Optional.of(productRepository.save(newProduct));

    }


    public Optional<Product> updateProductWithSingle(
                                                        String productName,
                                                        String productBrand,
                                                        List<String> descriptions,
                                                        Map<String,String> aboutProduct,
                                                        List<SingleCategory> divideProducts,
                                                        String divisionName
                                                    )
    {

        VariationV1<?> variation;
        Product newProduct;


        productBrand=productBrand.trim().toLowerCase();
        productName=productName.trim().toLowerCase();
        if(!checkProduct(productName,productBrand))
            throw new ProductNotExistException(productName,productBrand);

        Map<String, QuantityV1> divResult=divideProducts
                .stream()
                .collect( Collectors.toMap(
                                SingleCategory::value,
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
                      throw new ProductExistException(productName,productBrand);

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
        try {
            imageServiceExternal.saveImageMapper(newProduct.getProductID());
        }catch (ImageMapperExistException exist)
        {

            throw new ProductExistException(productName,productBrand);
        }


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
            throw new ProductNotExistException(productName,productBrand);

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
            throw new ProductNotExistException(productName,productBrand);
        return result;
    }

    public Optional<List<String>> filterCategories(String productName,String productBarnd)
    {
        Optional<Product> product;
        VariationType type;
        List<String> categories=new ArrayList<>();


        product=readProduct(productName,productBarnd);
        type=product.get().getVariationType();

        if(type.equals(VariationType.SINGLE))
        {
            ProductParser.ProductSingle result = parser.productToSingle(product);
            categories = result
                    .categories()
                    .stream()
                    .map((obj) -> obj.value())
                    .collect(Collectors.toList());
            return Optional.of(categories);
        }
        if(type.equals((VariationType.DOUBLE)))
        {
            ProductParser.ProductDouble result = parser.productToDouble(product);
              categories = result.categoriesWithSub()
                    .stream()
                    .map((obj->obj.mainCategories()))
                    .collect(Collectors.toList());


        }
          categories.add("main");
        return Optional.of(categories);

    }



    //Delete product

    public void deleteProduct(String productName,String productBrand)
    {
        productName=productName.trim().toLowerCase();
        productBrand=productBrand.trim().toLowerCase();

        if(!checkProduct(productName,productBrand))
            throw new ProductNotExistException(productName,productBrand);
        try {
            imageServiceExternal.removeImageMapper("Id_"+productName+productBrand);

        }catch (ImageMapperExistException exception)
        {
            //add exception if image mapper in not found
        }
        finally {
            productRepository.deleteById("Id_"+productName+productBrand);
        }

    }
  public byte[] getImage( String productName,
                          String productBrand,
                          String productCategory
                         )
  {
      productName=productName.trim().toLowerCase();
      productBrand=productBrand.trim().toLowerCase();
      if(!checkProduct(productName,productBrand))
          throw new ProductNotExistException(productName,productBrand);

     return imageServiceExternal.getImageFromMapper(
                                             "Id_"+productName+productBrand,
                                              productCategory);
  }

  public String setImage(String productName,
                         String productBrand,
                         String productCategory,
                         MultipartFile image)
  {
      Optional<List<String>> checkCategories;
      productName=productName.trim().toLowerCase();
      productBrand=productBrand.trim().toLowerCase();

      if(!checkProduct(productName,productBrand))
          throw new ProductNotExistException(productName,productBrand);
       checkCategories=filterCategories(productName,productBrand);

       if(checkCategories.isPresent())
           if(!checkCategories.get().contains(productCategory)) throw new ProductCategoryNotExist(productName,productBrand);

      return imageServiceExternal.setImageToMapper("Id_"+productName+productBrand,
          productCategory,
          image);



  }

 public Optional<List<ProductParser.ProductNone>> getAllProduct()
  {
      List<Product> allPrduct= productRepository.findAll();
      List<ProductParser.ProductNone> rerult;
      if(allPrduct.isEmpty())return Optional.of(List.of());

     rerult=allPrduct
             .stream()
             .map(product -> parser.productToNone(Optional.of(product)))
             .collect(Collectors.toList());
     return Optional.of(rerult);
  }


    //private methods


    private Boolean checkProduct(String productName,String brand)
    {

        return productRepository.findById("Id_"+productName+brand).isPresent();
    }


}