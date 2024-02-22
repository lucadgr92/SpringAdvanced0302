package com.develhope.SpringAdvanced0302.controllers;

import com.develhope.SpringAdvanced0302.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class DownloadController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/download")
    public byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        System.out.println("Downloading " + fileName);
        String extension = FilenameUtils.getExtension(fileName);

        switch(extension) {
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }

        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        return fileStorageService.download(fileName);
    }
}
