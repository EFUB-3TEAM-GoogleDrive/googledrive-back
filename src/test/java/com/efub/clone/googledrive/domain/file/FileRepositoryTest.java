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

import java.time.LocalDateTime;
import java.util.List;

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
    public void 파일저장_불러오기(){
        //given
        String filestring = "testimage.jpg";
        String filename = "testimage";
        String type = filestring.substring(filestring.lastIndexOf('.')+1);
        String filepath = "testfilepath";
        Long size = Long.valueOf(2);

        User user = User.builder()
                .userName("testuser")
                .profileImagePath("profileimage.jpg")
                .isSchool(false)
                .build();

        userRepository.save(user);

        fileRepository.save(File.builder()
                .filename(filename)
                .type(type)
                .size(size)
                .filepath(filepath)
                .user(user)
                .build());

        //when
        List<File> files = fileRepository.findAll();

        //then
        File file = files.get(0);
        assertThat(file.getFilename()).isEqualTo(filename);
        assertThat(file.getType()).isEqualTo(type);
        assertThat(file.getSize()).isEqualTo(size);
        assertThat(file.getFilepath()).isEqualTo(filepath);
        assertThat(file.getUser().getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .userName("testuser")
                .profileImagePath("profileimage.jpg")
                .isSchool(false)
                .build();

        userRepository.save(user);

        fileRepository.save(File.builder()
                .filename("testimg")
                .type("jpg")
                .size(Long.valueOf(2))
                .user(user)
                .build());

        //when
        List<File> files = fileRepository.findAll();

        //then
        File file = files.get(0);

        System.out.print(">>>>>>>> createdDate"+file.getCreatedDate()+
                ", modifiedDate="+file.getChangedDate());

        assertThat(file.getCreatedDate()).isAfter(now);
        assertThat(file.getChangedDate()).isAfter(now);
    }

}