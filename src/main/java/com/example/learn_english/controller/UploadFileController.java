package com.example.learn_english.controller;
import com.example.learn_english.service.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadFileController {
    private final UploadFile uploadFile;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<List<String>> uploadImages(@RequestParam("imageFiles") MultipartFile[] files) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = uploadFile.uploadFile(file);
            imagePaths.add(path);
        }
        return ResponseEntity.ok(imagePaths);
    }

}
