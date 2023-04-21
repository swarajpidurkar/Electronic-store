package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.exceptions.BadApiRequest;
import com.bikkadit.electronic.store.ElectronicStore.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@Slf4j
public class FileServiceImpl implements FileService {


    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        log.info("initiating the dao call for the save user Image ");

        //abc.png
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName :  {}",originalFilename);

        String filename= UUID.randomUUID().toString();
        String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension=filename + extension;
        String fullPathWithFileName=path + fileNameWithExtension;

        logger.info("full image path: { ",fullPathWithFileName);
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){

            /// file save
            logger.info("file extension is {}",extension);

            File folder =new File(path);

            if(!folder.exists()){
                // create the folder
                folder.mkdir();

            }
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            log.info("Completed the dao call for the save user Image");
            return fileNameWithExtension;


        }
        else{
            throw new BadApiRequest("File with this extension "+ extension + "not allowed");
        }




    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        log.info("initiating the dao call to get user Image");
        String fullpath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullpath);
        log.info("Completed the dao call to get user Image");

        return inputStream;
    }
}
