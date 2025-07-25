package com.ecom.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImp implements  FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

//        File Name Of Current / Original file
        String originalFileName = file.getOriginalFilename();

//        Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

//        check if path exist and Create
        File folder  = new File(path);
        if (!folder.exists()) {
            folder.mkdirs(); // <-- Fix here
        }
//      Upload To the Server
        Files.copy(file.getInputStream(), Paths.get(filePath));

//        Returning The File Name
          return fileName;

    }
}
