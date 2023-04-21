package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.ProductDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.Product;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.helper.Helper;
import com.bikkadit.electronic.store.ElectronicStore.repositories.ProductRepository;
import com.bikkadit.electronic.store.ElectronicStore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public ProductDto create(ProductDto productDto) {
        log.info("Initiating the dao call to save product");
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        Product product = mapper.map(productDto, Product.class);
        Product product1 = productRepository.save(product);
        log.info("Completed the dao call to save product");
        return mapper.map(product1, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        log.info("Initiating the dao call to update product{} :",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product is not found with the id u provided"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        Product product1 = productRepository.save(product);
        log.info("Completed the dao call to update product{} :",productId);
        return mapper.map(product1, ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        log.info("Initiating the dao call to delete product{} :",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product with this is not available"));
        log.info("Completed the dao call to delete product{} :",productId);
        productRepository.delete(product);
    }

    @Override
    public ProductDto getById(String productId) {
        log.info("Initiating the dao call to get product By ProductId {} :",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product with this is not available"));
        log.info("Completed the dao call to get product By ProductId{} :",productId);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        log.info("Initiating the dao call to get all product");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))  ? (Sort.by(sortBy).descending())  :(Sort.by(sortBy).ascending().ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        log.info("Completed the dao call to get all product");
        return Helper.getPageableResponse(page,ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        log.info("Initiating the dao call to get all product which are alive");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))  ? (Sort.by(sortBy).descending())  :(Sort.by(sortBy).ascending().ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        log.info("Completed the dao call to get all product which are alive");
        return Helper.getPageableResponse(page,ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        log.info("Initiating the dao call to get all product title {} :",subTitle);
        Sort sort=(sortDir.equalsIgnoreCase("desc"))  ? (Sort.by(sortBy).descending())  :(Sort.by(sortBy).ascending().ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        log.info("Completed the dao call to get all product title{} :",subTitle);
        return Helper.getPageableResponse(page,ProductDto.class);
    }
}
