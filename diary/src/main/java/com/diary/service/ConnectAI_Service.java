package com.diary.service;

import com.diary.dto.GetAIDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

//ai로부터 이미지를 받아오고 저장합니다.

@Service
public class ConnectAI_Service {
    private final RestTemplate restTemplate;

    public ConnectAI_Service(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /*
    public byte[] downloadImage(String URL, String diary_content) throws IOException{
        System.out.println("곧 보냅니다");
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(URL, diary_content, byte[].class);
        System.out.println("받기 완료!");
        return responseEntity.getBody();
    }
    */

    public GetAIDto getTheme(String URL, String content) throws IOException{
        System.out.println("AI서버로 다이어리 내용 전송");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Json화
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(Map.of("content", content));

        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        System.out.println("내용: "+ requestEntity);
        ResponseEntity<GetAIDto> responseEntity = restTemplate.postForEntity(URL, requestEntity, GetAIDto.class);
        System.out.println(responseEntity);
        return responseEntity.getBody();
    }



}
