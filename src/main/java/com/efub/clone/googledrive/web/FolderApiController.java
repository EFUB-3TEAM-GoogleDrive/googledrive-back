package com.efub.clone.googledrive.web;

import com.efub.clone.googledrive.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FolderApiController {
    private final FolderService folderService;

    @GetMapping("users/{userId}/folders")
    public ResponseEntity<Object> getFolders(@PathVariable Long userId)
    {
        try{
            return ResponseEntity.ok().body(folderService.getFolders(userId));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid user");
        }
    }

    @PostMapping("users/{userId}/folders")
    public ResponseEntity<Object> createFolders(@PathVariable Long userId,
                                                @RequestParam(value = "folderName") String folderName)
    {
        try {
            return ResponseEntity.ok().body(folderService.createFolder(userId, folderName));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid user");
        }
    }

    @DeleteMapping("users/{userId}/folders/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteFolder(@PathVariable Long userId,
                                             @RequestParam(value = "folderId") Long folderId)
    {
        try {
            return ResponseEntity.ok().body(folderService.deleteFolder(userId, folderId));
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }
}
