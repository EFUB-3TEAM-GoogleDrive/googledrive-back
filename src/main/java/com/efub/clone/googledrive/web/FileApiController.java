package com.efub.clone.googledrive.web;

import com.efub.clone.googledrive.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FileApiController {
    private final FileService fileService;

    @PostMapping("users/{userId}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> uploadFile(@PathVariable Long userId,
            @RequestParam(value = "file", required = true) MultipartFile file)
    {
        try{
            return ResponseEntity.ok().body(fileService.uploadFile(userId, file));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid user");
        }
    }
}