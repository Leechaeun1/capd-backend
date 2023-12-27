package com.diary.dto;


import com.diary.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
//여기선 스트링만 받기
public class LoginAfDto {
    private String userImage;
    private String userTheme;

    public LoginAfDto(Optional<UserEntity> db) throws IOException {

        userImage = db.get().getUsrProfile();
        userTheme = db.get().getUsrTheme();
    }

}
