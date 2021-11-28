package com.efub.clone.googledrive.domain.folder;

import com.efub.clone.googledrive.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name="folders")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folderId;

    private String folderName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean deleteFlag;

    private LocalDateTime deletedDate;

    @Builder
    private Folder(String folderName, User user){
        this.folderName = folderName;
        this.user = user;
        this.deleteFlag = false;
    }
}
