package com.efub.clone.googledrive.web.dto;

import com.efub.clone.googledrive.domain.file.File;
import com.efub.clone.googledrive.domain.folder.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseListDto {
    private Long id;
    private String name;
    private String type;
    private LocalDateTime deletedDate;

    public ResponseListDto(File entity){
        id = entity.getFileId();
        name = entity.getFilename();
        type = entity.getType();
        deletedDate = entity.getDeletedDate();
    }

    public ResponseListDto(Folder entity){
        id = entity.getFolderId();
        name = entity.getFolderName();
        type = "folder";
        deletedDate = entity.getDeletedDate();
    }
}
