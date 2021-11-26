package com.efub.clone.googledrive.service;

import com.efub.clone.googledrive.domain.file.File;
import com.efub.clone.googledrive.domain.file.FileRepository;
import com.efub.clone.googledrive.domain.user.User;
import com.efub.clone.googledrive.domain.user.UserRepository;
import com.efub.clone.googledrive.web.dto.FileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Service
public class FileService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Transactional
    public String uploadFile(
            MultipartFile file,
            FileRequestDto requestDto
    ) throws Exception{
        String filepath = s3Service.upload(file);
        User user = userRepository.findUserByUserId(requestDto.getUserId());

        if(user == null){
            throw new IllegalArgumentException();
        }

        File entity = File.builder()
                .filename(file.getOriginalFilename())
                .type(file.getContentType())
                .size(requestDto.getSize())
                .filepath(filepath)
                .user(user)
                .build();

        fileRepository.save(entity);
        return "success";
    }


    @Transactional
    public String deleteFile(String key) {
        s3Service.delete(key);

        return "delete";
    }
}
