package com.example.learn_english.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadFile {
    public String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("Không có file được chọn hoặc file rỗng.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IOException("Tên file không hợp lệ.");
        }

        String sourceFolder = "D:\\IdeaProjects\\BackEnd_SpringBoot\\learn_english\\src\\main\\resources\\static\\uploadFile\\";
        File folder = new File(sourceFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File sourceFile = new File(sourceFolder + fileName);

        FileCopyUtils.copy(file.getBytes(), sourceFile);

        return fileName;
    }

}
