package com.efub.clone.googledrive.web;

import com.efub.clone.googledrive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @GetMapping("users/{userId}/deleted")
    public ResponseEntity<Object> getDeleted(@PathVariable Long userId)
    {
        try{
            return ResponseEntity.ok().body(userService.getDeleted(userId));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid user");
        }
    }
}
