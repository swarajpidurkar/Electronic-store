package com.bikkadit.electronic.store.ElectronicStore.dtos;

import com.bikkadit.electronic.store.ElectronicStore.validate.ImageNameValidate;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {



    private String userId;



    @Size(min=3, max = 15,message = "invalid name !!")
    private String name;


    //@Email(message = "invalid user Email!!!")

    @Pattern(regexp = "^[a-z0-9][-a-z0-9,_]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "invalid user Email")
    @NotBlank(message = "Email is required")
    private String email;


    @NotBlank(message = "password is required")
    private String password;


    @Size(min=4,max=6,message = "invalid gender !! ")
    private String gender;



    @NotBlank(message = " Write someThing")
    private String about;


    // Pattern

    // custom validator

    @ImageNameValidate
    private String imageName;
}