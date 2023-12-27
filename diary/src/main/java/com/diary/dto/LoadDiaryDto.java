package com.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadDiaryDto {
    //  글 보내는 전용 dto입니다.
    private String title;
    private String body;
    private String emoji;
    private String theme;
    private String updatedAt;
}
