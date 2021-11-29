package com.efub.clone.googledrive.domain.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUserUserIdAndDeleteFlag(Long userId, Boolean deleteFlag);
    Folder findFolderByFolderId(Long folderId);
}
