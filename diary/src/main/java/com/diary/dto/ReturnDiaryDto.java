package com.diary.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnDiaryDto {   //다이어리 정보를 프론트한테 넘겨주기 위한 리스트를 만듭니다.
    private Long id;
    private String emoji;
    private int month;
    private int day;
}
