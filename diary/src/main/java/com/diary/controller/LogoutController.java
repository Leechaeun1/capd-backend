package com.diary.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        //request 객체를 통해 session을 추출
        HttpSession session = request.getSession();

        //세션 종료
        session.invalidate();
        return "로그아웃!";
    }
}
