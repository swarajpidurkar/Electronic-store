package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.Category;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.helper.AppConstants;
import com.bikkadit.electronic.store.ElectronicStore.helper.Helper;
import com.bikkadit.electronic.store.ElectronicStore.repositories.CategoryRepository;
import com.bikkadit.electronic.store.ElectronicStore.services.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCat(CategoryDto categoryDto) {
        log.info("initiating the dao call to save the category");
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setIsActive(AppConstants.YES);
        Category savedCategory = categoryRepository.save(category);
        log.info("Completed the dao call to save the category");
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto upadateCat(CategoryDto categoryDto, String categoryId) {
        log.info("initiating the dao call to update the category {} :",categoryId);
        // get category o f given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found exception"));
        // update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category category1 = categoryRepository.save(category);
        log.info("Completed the dao call to update the category{} :",categoryId);
        return modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public void deleteCat(String categoryId) {
        log.info("initiating the dao call to delete the category{} :",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found exception"));
        categoryRepository.delete(category);
        log.info("Completed the dao call to delete the category{} :",categoryId);


    }

    @Override
    public PageableResponse<CategoryDto> getAllCat(int pageNumber,int pageSize,String sortBy,String sortDir) {
        log.info("initiating the dao call to get all  categories");
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        log.info("Completed the dao call to get all  categories");
        return pageableResponse;
    }

    @Override
    public CategoryDto categoryById(String categoryId) {
        log.info("initiating the dao call to get  category by categoryId{} :",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found exception"));
        log.info("Completed the dao call to get  category by categoryId{} :",categoryId);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> searchCat(String keywords) {
//        List<Category> byNameContaining = categoryRepository.findByNameContaining(keywords);
//        CategoryDto categoryDto = modelMapper.map(byNameContaining, CategoryDto.class);
        return null;
    }


}
