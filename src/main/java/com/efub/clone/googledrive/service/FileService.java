package com.efub.clone.googledrive.service;

import com.efub.clone.googledrive.domain.file.File;
import com.efub.clone.googledrive.domain.file.FileRepository;
import com.efub.clone.googledrive.domain.user.User;
import com.efub.clone.googledrive.domain.user.UserRepository;
import com.efub.clone.googledrive.web.dto.FileResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class FileService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Transactional
    public String uploadFile(Long userId, MultipartFile file) throws Exception{
        String filepath = s3Service.upload(file);
        User user = userRepository.findUserByUserId(userId);

        if(user == null){
            throw new IllegalArgumentException();
        }

        File entity = File.builder()
                .filename(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filepath(filepath)
                .user(user)
                .build();
        fileRepository.save(entity);
        entity.setOpenedDate();

        return "success";
    }

    @Transactional(readOnly = true)
    public List<FileResponseListDto> getFiles(Long userId) throws Exception{
        User user = userRepository.findUserByUserId(userId);

        if(user == null){
            throw new IllegalArgumentException();
        }

        List<FileResponseListDto> files = fileRepository.findAllByUserUserIdAndDeleteFlag(userId, false)
                .stream()
                .map(FileResponseListDto::new)
                .collect(Collectors.toList());

        return files;
    }


    @Transactional
    public String deleteFile(Long userId, Long fileId) throws Exception{
        User user = userRepository.findUserByUserId(userId);

        if(user == null) {
            throw new IllegalArgumentException();
        }

        File file = fileRepository.getById(fileId);
        file.setDeleteFlag(true);

        fileRepository.save(file);
        return "success";
    }


    @Transactional
    public String downloadUrl(Long userId, Long fileId) {
        User user = userRepository.findUserByUserId(userId);

        if(user == null) {
            throw new IllegalArgumentException();
        }

        File file = fileRepository.getById(fileId);

        return file.getFilepath();
    }
}
