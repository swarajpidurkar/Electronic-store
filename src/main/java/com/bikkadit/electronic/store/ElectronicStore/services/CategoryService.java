package com.bikkadit.electronic.store.ElectronicStore.services;

import com.bikkadit.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    // create

    CategoryDto createCat(CategoryDto categoryDto);

    // update
    CategoryDto upadateCat(CategoryDto categoryDto,String CategoryId);

    // delete
    void deleteCat(String categoryId);

    // get all category

    PageableResponse<CategoryDto> getAllCat(int pageNumber,int pageSize,String sortBy,String sortDir);

    // get single

    CategoryDto categoryById(String categoryId);

    // search


    List<CategoryDto> searchCat(String keywords);
}
