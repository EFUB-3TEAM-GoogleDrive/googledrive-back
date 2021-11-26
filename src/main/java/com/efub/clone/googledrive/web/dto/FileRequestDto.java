package com.efub.clone.googledrive.web.dto;

import com.efub.clone.googledrive.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileRequestDto {
    private Long userId;
    private Double size;
}
