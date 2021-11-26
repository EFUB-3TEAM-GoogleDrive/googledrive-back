package com.efub.clone.googledrive.domain.folder;

import com.efub.clone.googledrive.domain.user.User;
import com.efub.clone.googledrive.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class FolderRepositoryTest {
    @Autowired
    FolderRepository folderRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void tearDown(){
        folderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 폴더저장_불러오기(){
        //given
        String folderName = "testfolder";

        User user = User.builder()
                .userName("testuser")
                .profileImagePath("profileimage.jpg")
                .isSchool(false)
                .build();

        userRepository.save(user);

        folderRepository.save(Folder.builder()
                .folderName(folderName)
                .user(user)
                .build());

        //when
        List<Folder> folders = folderRepository.findAll();

        //then
        Folder folder = folders.get(0);
        assertThat(folder.getFolderName()).isEqualTo(folderName);
        assertThat(folder.getUser().getUserId()).isEqualTo(user.getUserId());
    }
}