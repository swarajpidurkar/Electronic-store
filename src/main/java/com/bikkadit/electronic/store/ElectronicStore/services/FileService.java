package com.bikkadit.electronic.store.ElectronicStore.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService  {

    public String uploadFile(MultipartFile file,String path) throws IOException;

    InputStream getResource(String path, String name) throws FileNotFoundException;


}
