package com.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDto {
    //사용자 id와 일기id를 받아 글을 삭제합니다.(삭제는 activate를 0으로만듬)
    private String account; //사용자 id
    private Long id;      //일기 id
}
