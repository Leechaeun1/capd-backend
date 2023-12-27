package com.diary.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {
    private String userImage;
    private String msg;

    public ResponseDto(String uI, String m)
    {
        userImage = uI;
        msg = m;
    }
}
