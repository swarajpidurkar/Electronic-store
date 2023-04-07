package com.bikkadit.electronic.store.ElectronicStore.controller;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadit.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    // create

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        log.info("Entering the request for save category data");
        CategoryDto category = categoryService.createCat(categoryDto);
        log.info("Completed the request for save category data");
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }


    // update

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        log.info("Entering the request for update category data with userId {}:",categoryId);
        CategoryDto categoryDto1 = categoryService.upadateCat(categoryDto, categoryId);
        log.info("completed the request for update category data with userId {}:",categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }


    // delete

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategoy(@PathVariable String categoryId){
        log.info("Entering the request for delete category data with userId {}:",categoryId);
        categoryService.deleteCat(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category deleted successfully").status(HttpStatus.OK).success(true).build();
        log.info("Completed the request for delete category data with userId {}:",categoryId);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    // getAll

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value="pageNumber",defaultValue = "8",required = false)int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "0",required = false)int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBY,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        log.info("Entering the request for getting all categories data");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAllCat(pageNumber, pageSize, sortBY, sortDir);
        log.info("Completed the request for getting all categories data");
        return  new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //get single

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCatById(@PathVariable String categoryId){
        log.info("Entering the request for get single category data ny Id{}:",categoryId);
        CategoryDto categoryDto = categoryService.categoryById(categoryId);
        log.info("Completed the request for get single category data ny Id{}:",categoryId);
        return  new ResponseEntity<>(categoryDto,HttpStatus.OK);

    }

}
