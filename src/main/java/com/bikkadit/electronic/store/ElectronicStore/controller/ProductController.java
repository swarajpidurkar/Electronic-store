package com.bikkadit.electronic.store.ElectronicStore.controller;


import com.bikkadit.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.ProductDto;
import com.bikkadit.electronic.store.ElectronicStore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;


    // create

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        log.info("Entering the request to save product data");
        ProductDto productDto1 = productService.create(productDto);
        log.info("Completed the request to save product data");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);

    }

    // update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable String productId){
        log.info("Entering the request to update  product data by productId{}:",productId);
        ProductDto updatedProduct = productService.update(productDto, productId);
        log.info("Completed the request to update  product data by productId{}:",productId);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);


    }

    // delete

    @DeleteMapping("{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
        log.info("Entering the request to delete  product data by productId{}:",productId);
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("product is deleted succesfullly")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        log.info("Completed the request to delete  product data by productId{}:",productId);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);

    }

    // get single
    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId){
        log.info("Entering the request to get  product data by productId{}:",productId);
        ProductDto productDto = productService.getById(productId);
        log.info("Completed the request to get  product data by productId{}:",productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);

    }

    // get all

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value="pageNumber",defaultValue = "8",required = false)int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "0",required = false)int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBY,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){

        log.info("Entering the request to get all product data");
        PageableResponse<ProductDto> allProducts = productService.getAll(pageNumber, pageSize, sortBY, sortDir);
        log.info("Completed the request to get all product data");

        return new ResponseEntity<>(allProducts,HttpStatus.OK);

    }


    // get all live

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getLiveProducts(
            @RequestParam(value="pageNumber",defaultValue = "8",required = false)int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "0",required = false)int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBY,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){
        log.info("Entering the request to get all product data which are live");
        PageableResponse<ProductDto> allProducts = productService.getAllLive(pageNumber, pageSize, sortBY, sortDir);
        log.info("completed the request to get all product data which are live");
        return new ResponseEntity<>(allProducts,HttpStatus.OK);

    }


    // search

    @GetMapping("/search/{keywords}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProducts(
            @PathVariable String keywords,
            @RequestParam(value="pageNumber",defaultValue = "8",required = false)int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "0",required = false)int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBY,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){
        log.info("Entering the request to get all product data by keywords{}:",keywords);
        PageableResponse<ProductDto> allProducts = productService.searchByTitle(keywords,pageNumber, pageSize, sortBY, sortDir);
        return new ResponseEntity<>(allProducts,HttpStatus.OK);

    }


}
