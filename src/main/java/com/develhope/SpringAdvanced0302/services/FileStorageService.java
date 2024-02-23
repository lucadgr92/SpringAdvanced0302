package com.develhope.SpringAdvanced0302.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${fileUploadFolder}")
    private String fileRepositoryFolder;


    @SneakyThrows
    public String upload (MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;

        File finalFolder = new File(fileRepositoryFolder);
        if(!finalFolder.exists()) throw new IOException("Final folder doesn't exist.");
        if(!finalFolder.isDirectory()) throw new IOException("Final folder isn't a directory.");

        File finalDestination = new File(fileRepositoryFolder + "\\" + completeFileName);
        if(finalDestination.exists()) throw new IOException("File conflict.");
        file.transferTo(finalDestination);
        return completeFileName;
    }

    @SneakyThrows
    public byte[] download(String fileName) {
        File fileFromRepository = new File(fileRepositoryFolder+"\\"+fileName);
        if(!fileFromRepository.exists()) throw new IOException("File doesn't exist.");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }



}
