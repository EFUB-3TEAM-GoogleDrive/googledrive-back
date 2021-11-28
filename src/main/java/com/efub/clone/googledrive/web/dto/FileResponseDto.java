package com.efub.clone.googledrive.web.dto;

import com.efub.clone.googledrive.domain.file.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponseDto {
	private Long fileId;
	private String filename;

	private Long folderId;
	private String folderName;

	public FileResponseDto(File entity) {
		this.fileId = entity.getFileId();
		this.filename = entity.getFilename();

		this.folderId = entity.getFolder().getFolderId();
		this.folderName = entity.getFolder().getFolderName();
	}

}
