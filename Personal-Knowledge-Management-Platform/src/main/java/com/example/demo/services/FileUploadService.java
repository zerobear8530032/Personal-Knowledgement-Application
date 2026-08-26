package com.example.demo.services;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class FileUploadService {
    final static String ROOTPATH="/uploads";
    public boolean upload(MultipartFile file, Long userId) throws IOException{
        Path rootPath = Path.of(ROOTPATH);
        rootPath=rootPath.resolve(userId.toString()).toAbsolutePath();
        Path filePath = Path.of(file.getName());
        filePath= filePath.normalize().toAbsolutePath();
        if(!filePath.startsWith(rootPath)){
            throw new SecurityException("Sorry incorrect file name ");
        }
        return true;
    }
}
