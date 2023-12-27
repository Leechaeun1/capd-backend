package com.diary.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewriteDto {
    private String title;   //일기제목
    private String body;    //일기내용
    private String emoji;   //감정표현
    private String summed;  //요약
    private String theme;   //테마
    private String account; //사용자 id
    private Long id;      //일기 id
}
