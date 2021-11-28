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
            @RequestParam(value = "file") MultipartFile file)
    {
        try{
            return ResponseEntity.ok().body(fileService.uploadFile(userId, file));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid user");
        }
    }

    @GetMapping("users/{userId}/files")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getFiles(@PathVariable Long userId)
    {
        try{
            return ResponseEntity.ok().body(fileService.getFiles(userId));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid user");
        }
    }

    @DeleteMapping("users/{userId}/files/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteFile(@PathVariable Long userId,
                                             @RequestParam(value = "fileId") Long fileId)
    {
        try {
            return ResponseEntity.ok().body(fileService.deleteFile(userId, fileId));
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }

    @GetMapping("users/{userId}/files/download")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> downloadFile(@PathVariable Long userId,
                                               @RequestParam(value = "fileId") Long fileId) {
            try {
                return ResponseEntity.ok().body("download link: " + fileService.downloadUrl(userId, fileId));
            } catch(Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 찾을 수 없습니다.");
            }
    }

    @PatchMapping("users/{userId}/files/{fileId}/folders")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> moveFolderOfFile(@PathVariable Long userId,
                                                   @PathVariable Long fileId,
                                                   @RequestParam(value = "folderId") Long folderId)
    {
        try {
            return ResponseEntity.ok().body(fileService.moveFolder(userId, fileId, folderId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("존재하지 않는 폴더입니다.");
        }
    }
}
