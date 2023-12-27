package com.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAIDto {
    String url1;
    String url2;
    String url3;
    String url4;
    String summed;

    GetAIDto(GetAIDto dto){
        this.url1 = dto.getUrl1();
        this.url2 = dto.getUrl2();
        this.url3 = dto.getUrl3();
        this.url4 = dto.getUrl4();
        this.summed = dto.getSummed();
    }
}
