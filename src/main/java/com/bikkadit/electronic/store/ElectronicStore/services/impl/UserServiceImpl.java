package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.User;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.helper.AppConstants;
import com.bikkadit.electronic.store.ElectronicStore.helper.Helper;
import com.bikkadit.electronic.store.ElectronicStore.repositories.UserRepository;
import com.bikkadit.electronic.store.ElectronicStore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("initiating the dao call for the save user data");

        // generate unique id in long format
        String UserId = UUID.randomUUID().toString();
        userDto.setUserId(UserId);

        // dtoToEntity
        User user=dtoToEntity(userDto);
        user.setIsActive(AppConstants.YES);
        User savedUser=userRepository.save(user);

        // entityToDto
        UserDto newDto=entityToDto(savedUser);
        log.info("Completed the dao call for the save user data");
        return newDto;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("initiating the dao call for the update user data {}:",userId);
        User user1=dtoToEntity(userDto);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with this id"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());

        User savedUser =userRepository.save(user);
        UserDto newDto=entityToDto(savedUser);
        log.info("completed the dao call for the save user data {}:",userId);
        return newDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with this id"));

        log.info("initiating the dao call to delete user data {}:",userId);

        // delete profile user image

       // images/user/abc.png
        String fullPath = imagePath + user.getImageName();


        try {
            Path path= Paths.get(fullPath);
            Files.delete(path);
        }
        catch(NoSuchFileException ex){
            logger.info("User image not found in the folder");
            ex.getStackTrace();


        }
        catch(IOException e) {
            throw new RuntimeException(e);

        }


        // delete user
        userRepository.delete(user);
        log.info("completed the dao call to delete  user data {}:",userId);


    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("initiating the dao call for get all user data");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        // default page number starts form 0
        Pageable pageable= PageRequest.of(pageNumber-1,pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        log.info("Completed the dao call for get all user data");

        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        log.info("initiating the dao call for get  user data by userId {}:",userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with this userid"));
        log.info("Completed the dao call for get all user data {}:",userId);
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("initiating the dao call for get  user data by email {}:",email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found with this email and pass"));
        log.info("Completed the dao call for get  user data by userId {}:",email);
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("initiating the dao call for get  user data by keywords {}:",keyword);
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("Completed the dao call for get  user data by keywords {}:",keyword);
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .imageName(savedUser.getImageName())
//                .build();

        return modelMapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user=User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .imageName(userDto.getImageName())
//                .build();

        return modelMapper.map(userDto,User.class);
    }




}
