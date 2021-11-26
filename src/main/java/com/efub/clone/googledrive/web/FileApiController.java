package com.efub.clone.googledrive.web;

import com.efub.clone.googledrive.service.FileService;
import com.efub.clone.googledrive.web.dto.FileRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FileApiController {
    private final FileService fileService;

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
}
