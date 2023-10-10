package com.ecom.product_catelog.api.product_images;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductImageController {
    @GetMapping("/image")

    public ResponseEntity<byte[]> setImagesId()
    {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
      //  httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity=new HttpEntity<>(httpHeaders);

      ResponseEntity<byte[]> img;
    try {


         return   restTemplate.exchange(
                "http://194.163.40.229:8089/api/v1/product/image/mapper/aslam/pic",
                HttpMethod.GET,
                entity,
                byte[].class

        );
    }catch (HttpClientErrorException e)
    {
         System.out.println("imge");

    }


        return  new ResponseEntity<>(null,HttpStatus.CONFLICT);
    }

    @GetMapping("/image/op")

    public ResponseEntity<?> getAllImages()
    {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity=new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(
                "http://194.163.40.229:8089/api/v1/product/image/mapper/aslam",
                HttpMethod.GET,
                entity,
                ArrayList.class
        );
    }


}
