package com.spring.crudauth.controller;

import com.spring.crudauth.Dto.ApiStatusOut;
import com.spring.crudauth.Dto.ProductRequest;
import com.spring.crudauth.Dto.ResponseOut;
import com.spring.crudauth.Entity.ProductEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.crudauth.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<ResponseOut> getAllProduct(){
        ResponseOut responseOut = new ResponseOut();
        ApiStatusOut apiStatusOut = new ApiStatusOut();
        Map<String , Object> data = new HashMap<>();

        List<ProductEntity> productEntityList =  productService.findAll();

        if(!productEntityList.isEmpty()){
            apiStatusOut.setCode("0000");
            apiStatusOut.setMessage("Success");
            data.put("data",productEntityList);

        }else{
            apiStatusOut.setCode("4000");
            apiStatusOut.setMessage("Fail to find All Data");
        }

        responseOut.setData(data);
        responseOut.setApistatus(apiStatusOut);
        return ResponseEntity.status(HttpStatus.OK).body(responseOut);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseOut> getByIdProduct(@PathVariable("id") int id){
        ResponseOut responseOut = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseOut);
    }


    @PostMapping("/product")
    public ResponseEntity<ResponseOut> createProduct(@RequestBody ProductRequest productRequest){
        ResponseOut responseOut = productService.saveProduct(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseOut);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ResponseOut> updateProduct(@PathVariable("id") int id , @RequestBody ProductRequest productRequest){
        ResponseOut responseOut = productService.updateProduct(id,productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseOut);
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseOut> deleteProduct(@PathVariable("id") int id){
        ResponseOut responseOut = productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseOut);
    }






}
