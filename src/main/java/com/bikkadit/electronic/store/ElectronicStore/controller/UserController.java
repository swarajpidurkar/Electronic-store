package com.bikkadit.electronic.store.ElectronicStore.controller;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadit.electronic.store.ElectronicStore.dtos.ImageResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.services.FileService;
import com.bikkadit.electronic.store.ElectronicStore.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger= LoggerFactory.getLogger(UserController.class);
    

    // create

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto){
        logger.info("Entering the request for save user data");
      UserDto userDto1 = userService.createUser(userDto);
      logger.info("completed the request for save user data");
      return new ResponseEntity<UserDto>(userDto1, HttpStatus.CREATED);


    }


    // update


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userDto,@PathVariable String userId){
        logger.info("Entering the request for update user data with userId{}:",userId);

        UserDto userDto1 = userService.updateUser(userDto, userId);
        logger.info("Completed the request for update user data with userId{}:",userId);


        return new ResponseEntity<>(userDto1,HttpStatus.OK);


    }

    //delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
        logger.info("Entering the request for delete user data with userId{}:",userId);

        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.
                builder().
                message("user deleted successfully !!").
                success(true).
                status(HttpStatus.OK).
                build();
        logger.info("Completed the request for delete user data with userId{}:",userId);



        return new ResponseEntity<ApiResponseMessage>(message,HttpStatus.OK);


    }



    // get all users
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue="10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue="asc",required = false) String sortDir

    ){
        logger.info("Entering the request for get all users data ");


        PageableResponse<UserDto> allUser = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed the request for get all users data ");

        return new ResponseEntity<PageableResponse<UserDto>>(allUser,HttpStatus.OK);

    }

    // get user by id

    @GetMapping("/userById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        logger.info("Entering the request for get user data with userId{}:",userId);


        UserDto userById = userService.getUserById(userId);
        logger.info("completed the request for get user data with userId{}:",userId);

        return new ResponseEntity<UserDto>(userById,HttpStatus.OK);
        
    }



    // get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email){

        logger.info("Entering the request for get user data by Email {}  :",email);
        UserDto userByEmail = userService.getUserByEmail(email);
        logger.info("Completed the request for get user data by Email {}  :",email);
        return new ResponseEntity<UserDto>(userByEmail,HttpStatus.OK);


    }




    // search user

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords){
        logger.info("Entering the request for get user data by keywords {}  :",keywords);
        List<UserDto> userDtos = userService.searchUser(keywords);
        logger.info("Completed the request for get user data by keywords {}  :",keywords);
        return  new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
    }


    // upload user image

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(
            @RequestParam("userImage")MultipartFile image,
            @PathVariable String userId
            ) throws IOException {
        logger.info("Entering the request for upload user image with userId{}  :",image,userId);

        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);


        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName)
                .success(true).status(HttpStatus.CREATED).build();
        logger.info("completed the request for upload user image with userId{}  :",image,userId);


        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);



    }





    // get user image

    @GetMapping("/image1/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {


        logger.info("Entering the request to get  image with userId{}  :",userId);

        UserDto userById = userService.getUserById(userId);
        logger.info("User image name :{}", userById.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, userById.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("completed the request to get  image with userId{}  :",userId);


    }

}
