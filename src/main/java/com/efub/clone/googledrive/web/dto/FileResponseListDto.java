package com.efub.clone.googledrive.web.dto;

import com.efub.clone.googledrive.domain.file.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FileResponseListDto {
    private Long fileId;
    private String filename;
    private String type;
    private LocalDateTime createdDate;
    private LocalDateTime openedDate;
    private LocalDateTime changedDate;

    public FileResponseListDto(File entity){
        this.fileId = entity.getFileId();
        this.filename = entity.getFilename();
        this.type = entity.getType();
        this.createdDate = entity.getCreatedDate();
        this.openedDate = entity.getOpenedDate();
        this.changedDate = entity.getChangedDate();
    }
}
