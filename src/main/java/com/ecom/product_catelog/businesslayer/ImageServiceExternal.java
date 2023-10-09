package com.ecom.product_catelog.businesslayer;

import com.ecom.product_catelog.exceptions.images.ImageMappeException;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ImageServiceExternal {
    @Autowired
    RestTemplate template;

    private  String url="http://194.163.40.229:8089";
    public String saveImageMapper(String imageMapperId) throws ImageMappeException
    {
        String path=url+"/api/v1/product/image/mapper/"+imageMapperId;
        ResponseEntity<String> response;
        HttpEntity<String> entity=new HttpEntity<>(new HttpHeaders());
        try {


                response= template.exchange( path,
                                             HttpMethod.POST,
                                             entity,
                                             String.class
                                            );


          }catch (HttpClientErrorException e)
            {
               throw new ImageMappeException(e.getMessage(),e.getStatusCode());
            }
        return response.getBody();
    }
    public String removeImageMapper(String imageMapperId)
    {
        String path=url+"/api/v1/product/image/mapper/"+imageMapperId;
        ResponseEntity<String> response;
        HttpEntity<String> entity=new HttpEntity<>(new HttpHeaders());
        try{
             response=template.exchange( path,
                                         HttpMethod.DELETE,
                                         entity,
                                         String.class

                                      );
        }catch (HttpClientErrorException exception)
        {
            throw new ImageMappeException(exception.getMessage(),exception.getStatusCode());
        }
        return response.getBody();
    }



}
