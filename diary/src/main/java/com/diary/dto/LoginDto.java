package com.diary.dto;
//회원가입 요청 받는 값 저장

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    private String account;
    private String password;
}
