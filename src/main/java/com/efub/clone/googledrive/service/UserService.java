package com.efub.clone.googledrive.service;

import com.efub.clone.googledrive.domain.file.FileRepository;
import com.efub.clone.googledrive.domain.folder.FolderRepository;
import com.efub.clone.googledrive.domain.user.User;
import com.efub.clone.googledrive.domain.user.UserRepository;
import com.efub.clone.googledrive.web.dto.ResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;

    @Transactional(readOnly = true)
    public List<ResponseListDto> getDeleted(Long userId) throws Exception{
        User user = userRepository.findUserByUserId(userId);

        if(user == null){
            throw new IllegalArgumentException();
        }

        List<ResponseListDto> deletedList = new ArrayList<ResponseListDto>();

        deletedList.addAll(folderRepository.findAllByUserUserIdAndDeleteFlag(userId, true)
                .stream()
                .map(ResponseListDto::new)
                .collect(Collectors.toList()));

        deletedList.addAll(fileRepository.findAllByUserUserIdAndDeleteFlag(userId, true)
                .stream()
                .map(ResponseListDto::new)
                .collect(Collectors.toList()));

        deletedList.sort(Comparator.comparing(ResponseListDto::getDeletedDate).reversed());

        return deletedList;
    }
}
