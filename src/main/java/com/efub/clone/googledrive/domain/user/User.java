package com.efub.clone.googledrive.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String userName;

    @Column
    private String profileImagePath;

    @Column(name = "school_flag")
    private Boolean isSchool = false;

    @Builder
    public User(String userName, String profileImagePath, Boolean isSchool){
        this.userName = userName;
        this.profileImagePath = profileImagePath;
        this.isSchool = isSchool;
    }
}
