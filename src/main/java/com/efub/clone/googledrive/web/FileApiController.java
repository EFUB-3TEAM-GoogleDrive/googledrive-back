package com.efub.clone.googledrive.web;

import com.efub.clone.googledrive.service.FileService;
import com.efub.clone.googledrive.service.S3Service;
import com.efub.clone.googledrive.web.dto.FileRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileApiController {
    private final FileService fileService;
    private final S3Service s3Service;

    @PostMapping("/files")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> uploadFile(
            @RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "requestDto") String requestDtoString)
    {
        try{
            FileRequestDto requestDto = new ObjectMapper().readValue(requestDtoString, FileRequestDto.class);
            return ResponseEntity.ok().body(fileService.uploadFile(file, requestDto));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }


    @GetMapping("/files/{fileName}/download")
    public ResponseEntity<byte[]> downloadFile(
        @RequestParam(value = "fileName", required = true) String fileName
    ) throws IOException {
        return s3Service.getObject(fileName);
    }


    @DeleteMapping("/files/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> deleteFile(
        @RequestParam(value = "fileId", required = true) Long file_id,
        @RequestParam(value = "requestDto") String requestDtoString)
    {
        try {
            FileRequestDto requestDto = new ObjectMapper().readValue(requestDtoString, FileRequestDto.class);
            return ResponseEntity.ok().body(fileService.deleteFile(file_id, requestDto));
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }
}
