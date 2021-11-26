package com.efub.clone.googledrive.domain.file;

import com.efub.clone.googledrive.domain.BaseTimeEntity;
import com.efub.clone.googledrive.domain.folder.Folder;
import com.efub.clone.googledrive.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name="files")
public class File extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String filename;

    private String type;

    private Double size;

    private Boolean deleteFlag;

    private LocalDateTime openedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @Builder
    private File(String filename, String type, Double size, User user, Folder folder){
        this.filename = filename;
        this.type = type;
        this.size = size;
        this.deleteFlag = false;
        this.openedDate = this.getCreatedDate();
        this.user = user;
        this.folder = folder;
    }
}
