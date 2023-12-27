package com.diary.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WriteDto {
    //    id, title, body, emoji, summed, createdAt
    //  받아오는 전용 dto입니다.
    //  일기제목, 일기 내용, 감정표현, 아이디를 받아올 dto입니다.
    private String title;
    private String body;
    private String emoji;
    private String summed;
    private String theme;
    private String account;
}
