package com.efub.clone.googledrive.domain.file;

import com.efub.clone.googledrive.domain.user.User;
import com.efub.clone.googledrive.domain.user.UserRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileRepositoryTest {
    @Autowired
    FileRepository fileRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void tearDown(){
        fileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 데이터주입테스트(){
        //given
        String filestring = "testimage.jpg";
        String filename = "testimage";
        String type = filestring.substring(filestring.lastIndexOf('.')+1);
        Double size = 2.0;

        User user = User.builder()
                .userName("testuser")
                .profileImagePath("profileimage.jpg")
                .isSchool(false)
                .build();

        userRepository.save(user);

        File entity = File.builder()
                .filename(filename)
                .type(type)
                .size(size)
                .user(user)
                .build();

        //when
        File file = fileRepository.save(entity);

        //then
        assertThat(file.getFilename()).isEqualTo(filename);
        assertThat(file.getType()).isEqualTo(type);
        assertThat(file.getSize()).isEqualTo(size);
        assertThat(file.getUser().getUserId()).isEqualTo(user.getUserId());
    }
}