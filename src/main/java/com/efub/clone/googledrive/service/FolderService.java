package com.efub.clone.googledrive.service;

import com.efub.clone.googledrive.domain.folder.Folder;
import com.efub.clone.googledrive.domain.folder.FolderRepository;
import com.efub.clone.googledrive.domain.user.User;
import com.efub.clone.googledrive.domain.user.UserRepository;
import com.efub.clone.googledrive.web.dto.FolderResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<FolderResponseListDto> getFolders(Long userId) throws IllegalArgumentException{
        CheckInvalidUser(userId);

        return folderRepository.findAllByUserUserIdAndDeleteFlag(userId, false)
                .stream()
                .map(FolderResponseListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public String createFolder(Long userId, String folderName) {
        CheckInvalidUser(userId);

        Folder entity = Folder.builder()
                .folderName(folderName)
                .user(userRepository.findUserByUserId(userId))
                .build();

        folderRepository.save(entity);

        return "폴더가 생성되었습니다.";
    }

    @Transactional
    public String deleteFolder(Long userId, Long folderId) throws Exception{
        CheckInvalidUser(userId);
        CheckInvalidFolder(folderId);

        Folder folder = folderRepository.findFolderByFolderId(folderId);
        folder.setDeleteFlag(true);
        folderRepository.save(folder);

        return "폴더가 휴지통으로 이동되었습니다.";
    }


    private void CheckInvalidUser(Long userId) throws IllegalArgumentException {
        User user = userRepository.findUserByUserId(userId);

        if(user == null) {
            throw new IllegalArgumentException();
        }
    }

    private void CheckInvalidFolder(Long folderId) throws FileNotFoundException {
        Folder folder = folderRepository.findFolderByFolderId(folderId);

        if(folder == null || folder.getDeleteFlag()) {
            throw new FileNotFoundException();
        }
    }
}
