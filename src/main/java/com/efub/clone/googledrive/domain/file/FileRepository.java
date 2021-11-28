package com.efub.clone.googledrive.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	File findFileByFileId(Long fileId);
    List<File> findAllByUserUserIdAndDeleteFlag(Long userId, Boolean deleteFlag);
}
