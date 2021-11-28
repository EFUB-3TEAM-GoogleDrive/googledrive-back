package com.efub.clone.googledrive.service;

import com.efub.clone.googledrive.domain.file.File;
import com.efub.clone.googledrive.domain.file.FileRepository;
import com.efub.clone.googledrive.domain.folder.Folder;
import com.efub.clone.googledrive.domain.folder.FolderRepository;
import com.efub.clone.googledrive.domain.user.User;
import com.efub.clone.googledrive.domain.user.UserRepository;
import com.efub.clone.googledrive.web.dto.FileResponseDto;
import com.efub.clone.googledrive.web.dto.FileResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class FileService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final S3Service s3Service;

    @Transactional
    public String uploadFile(Long userId, MultipartFile file) throws Exception{
        String filepath = s3Service.upload(file);
        CheckInvalidUser(userId);

        File entity = File.builder()
                .filename(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .filepath(filepath)
                .user(userRepository.findUserByUserId(userId))
                .build();

        fileRepository.save(entity);
        entity.setOpenedDate();

        return "success";
    }


    @Transactional(readOnly = true)
    public List<FileResponseListDto> getFiles(Long userId) throws IllegalArgumentException {
        CheckInvalidUser(userId);

        return fileRepository.findAllByUserUserIdAndDeleteFlag(userId, false)
                .stream()
                .map(FileResponseListDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public String deleteFile(Long userId, Long fileId) throws Exception{
        CheckInvalidUser(userId);
        CheckInvalidFile(fileId);

        File file = fileRepository.findFileByFileId(fileId);
        file.setDeleteFlag(true);
        fileRepository.save(file);

        return "파일이 휴지통으로 이동되었습니다.";
    }


    @Transactional
    public String downloadUrl(Long userId, Long fileId) throws FileNotFoundException {
        CheckInvalidUser(userId);
        CheckInvalidFile(fileId);

        File file = fileRepository.findFileByFileId(fileId);
        return file.getFilepath();
    }


    @Transactional
    public FileResponseDto moveFolder(Long userId, Long fileId, Long folderId) throws Exception {
        CheckInvalidUser(userId);
        CheckInvalidFile(fileId);
        CheckInvalidFolder(folderId);

        File file = fileRepository.findFileByFileId(fileId);

        file.getFolder().setFolderId(folderId);
        fileRepository.save(file);

        return new FileResponseDto(file);
    }


    // Error Checking function to refactor
    private void CheckInvalidUser(Long userId) throws IllegalArgumentException {
        User user = userRepository.findUserByUserId(userId);

        if(user == null) {
            throw new IllegalArgumentException();
        }
    }

    private void CheckInvalidFile(Long fileId) throws FileNotFoundException {
        File file = fileRepository.findFileByFileId(fileId);

        if(file == null || file.getDeleteFlag()) {
            throw new FileNotFoundException();
        }
    }

    private void CheckInvalidFolder(Long folderId) throws Exception {
        Folder folder = folderRepository.findFolderByFolderId(folderId);

        if(folder == null || folder.getDeleteFlag()) {
            throw new Exception("폴더를 찾을 수 없습니다");
        }
    }
}
