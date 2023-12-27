package com.diary.controller;

import com.diary.dto.LoginDto;
import com.diary.dto.ResponseDto;
import com.diary.dto.SignUpDto;
import com.diary.dto.ThemeChangeDto;
import com.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired UserService userService;

    //회원가입
    @PostMapping("/new")
    public ResponseEntity<?> signup(@RequestBody SignUpDto requestBody){
        System.out.println(requestBody.toString());
        ResponseEntity<?> result = userService.signUp(requestBody);
        //ResponseDto<?> result = userService.signUp(requestBody);
        return result;
    }

    //로그인
    @PostMapping("/{account}")
    public ResponseEntity<?> login(@RequestBody LoginDto requestBody){
        System.out.println(requestBody.toString());
        ResponseEntity<?> result = userService.logIn(requestBody);
        return result;
    }

    //로그인시 계정 정보넘기기
    @GetMapping("/{account}")
    public ResponseEntity<?> accountInfo(@PathVariable String account){
        System.out.println(account);
        ResponseEntity<?> result = userService.logInAf(account);
        return result;
    }

    //비밀번호 변경 (LoginDto가 account, password를 받는 객체라서 이거썼습니다.)
    @PostMapping("/changePw")
    public ResponseEntity<?> changePw(@RequestBody LoginDto dto)
    {
        System.out.println(dto.toString());
        String account = dto.getAccount();
        String newPw = dto.getPassword();
        ResponseEntity<?> result = userService.changePw(account, newPw);
        return result;
    }


    @PostMapping("/theme")
    public ResponseEntity<?> changeTheme(@RequestBody ThemeChangeDto dto)
    {
        System.out.println(dto.toString());
        //작업
        String account = dto.getAccount();
        String theme = dto.getTheme();
        ResponseEntity<?> result = userService.changeTheme(account, theme);
        return result;
    }


    // /upload : 프로필 사진 변경
    @PostMapping("/upload/{account}.jpg")
    public ResponseEntity<ResponseDto> uploadImage(@PathVariable String account, @RequestParam("image") MultipartFile file) {
        ResponseEntity<ResponseDto> result = userService.uploadUsrImage(account, file);
        return result;
    }


}
