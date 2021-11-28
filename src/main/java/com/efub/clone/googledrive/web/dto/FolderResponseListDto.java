package com.efub.clone.googledrive.web.dto;

import com.efub.clone.googledrive.domain.folder.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderResponseListDto {
    private Long folderId;
    private String folderName;

    public FolderResponseListDto(Folder entity){
        this.folderId = entity.getFolderId();
        this.folderName = entity.getFolderName();
    }
}
