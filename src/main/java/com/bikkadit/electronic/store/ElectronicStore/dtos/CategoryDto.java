package com.bikkadit.electronic.store.ElectronicStore.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {



    private String categoryId;

    @NotBlank
    @Min(value=4,message = "title must of minimum 4 characters")
    private String title;

    @NotBlank(message = "Description is requires !! ")

    private String description;


    private String coverImage;

}
