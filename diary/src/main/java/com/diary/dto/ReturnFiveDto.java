package com.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnFiveDto {
    //글 5개를 넘겨주기위한 박스입니다.
    private String title;
    private String body;
    private String createdAt;
    private Long id;
}
