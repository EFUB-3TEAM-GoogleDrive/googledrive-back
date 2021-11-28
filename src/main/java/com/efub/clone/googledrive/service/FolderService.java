package com.efub.clone.googledrive.service;

import com.efub.clone.googledrive.domain.folder.FolderRepository;
import com.efub.clone.googledrive.domain.user.User;
import com.efub.clone.googledrive.domain.user.UserRepository;
import com.efub.clone.googledrive.web.dto.FileResponseListDto;
import com.efub.clone.googledrive.web.dto.FolderResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<FolderResponseListDto> getFolders(Long userId) throws Exception{
        User user = userRepository.findUserByUserId(userId);

        if(user == null){
            throw new IllegalArgumentException();
        }

        List<FolderResponseListDto> files = folderRepository.findAllByUserUserIdAndDeleteFlag(userId, false)
                .stream()
                .map(FolderResponseListDto::new)
                .collect(Collectors.toList());

        return files;
    }
}
