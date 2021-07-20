package com.spring.crudauth.service;

import com.spring.crudauth.Dto.ApiStatusOut;
import com.spring.crudauth.Dto.ProductRequest;
import com.spring.crudauth.Dto.ResponseOut;
import com.spring.crudauth.Entity.ProductEntity;
import com.spring.crudauth.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findAll(){
        return productRepository.findAll();
    }


    public ResponseOut getProductById(int id){
        ResponseOut responseOut = new ResponseOut();
        Map<String , Object> data = new HashMap<>();
        ApiStatusOut apiStatusOut = new ApiStatusOut();


            Optional<ProductEntity> productEntity = productRepository.findById(Long.valueOf(id));
            if (productEntity.isPresent()) {
                data.put("data", productEntity.get());

                apiStatusOut.setCode("0000");
                apiStatusOut.setMessage("Success");

                responseOut.setData(data);


            } else {
                apiStatusOut.setCode("4000");
                apiStatusOut.setMessage("Fail");
            }


        responseOut.setApistatus(apiStatusOut);


        return responseOut;
    }


    public ResponseOut saveProduct(ProductRequest productRequest){
        ResponseOut responseOut = new ResponseOut();
        Map<String , Object> data = new HashMap<>();
        ApiStatusOut apiStatusOut = new ApiStatusOut();
        ProductEntity productEntity = new ProductEntity();

        if(Objects.nonNull(productRequest)) {
            productEntity.setPrice(productRequest.getPrice());
            productEntity.setName(productRequest.getProductName());
            productEntity.setStock(productRequest.getStock());

            productRepository.save(productEntity);

            apiStatusOut.setCode("0000");
            apiStatusOut.setMessage("Success");

        }else{
            apiStatusOut.setCode("4000");
            apiStatusOut.setMessage("Fail");
        }


        responseOut.setApistatus(apiStatusOut);


        return responseOut;
    }


    public ResponseOut updateProduct(int id,ProductRequest productRequest){
        ResponseOut responseOut = new ResponseOut();
        Map<String , Object> data = new HashMap<>();
        ApiStatusOut apiStatusOut = new ApiStatusOut();
        ProductEntity productEntity = new ProductEntity();

        if(Objects.nonNull(productRequest)) {
            Optional<ProductEntity> productEntity1 = productRepository.findById(Long.valueOf(id));
            if(productEntity1.isPresent()){
                ProductEntity result = productEntity1.get();
                result.setPrice(productRequest.getPrice());
                result.setName(productRequest.getProductName());
                result.setStock(productRequest.getStock());

                productRepository.save(productEntity);
                apiStatusOut.setCode("0000");
                apiStatusOut.setMessage("Success");
                responseOut.setData(data);
            }else{
                apiStatusOut.setCode("4000");
                apiStatusOut.setMessage("Fail");
            }
        }else{
            apiStatusOut.setCode("4000");
            apiStatusOut.setMessage("Fail");
        }


        responseOut.setApistatus(apiStatusOut);


        return responseOut;
    }


    public ResponseOut deleteProduct(int id){
        ResponseOut responseOut = new ResponseOut();
        Map<String , Object> data = new HashMap<>();
        ApiStatusOut apiStatusOut = new ApiStatusOut();



            productRepository.deleteById(Long.valueOf(id));

            apiStatusOut.setCode("0000");
            apiStatusOut.setMessage("Success");



        responseOut.setApistatus(apiStatusOut);

        return responseOut;
    }



}
