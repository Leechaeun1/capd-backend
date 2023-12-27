package com.diary.controller;

import com.diary.dto.*;
import com.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    @Autowired
    DiaryService diaryService;

    /*
    @GetMapping("/{account}")
    public ResponseEntity<String> postForm() {
        return ResponseEntity.ok("WritePage");
    }*/

    //인공지능으로부터 테마사진 불러오기
    @PostMapping("/getTheme")
    public ResponseEntity<?> getTheme(@RequestBody GetThemeDto dto) {
        System.out.println("글내용: "+dto.toString());
        ResponseEntity<?> result = diaryService.getTheme(dto);
        return result;
    }

    //글 저장하기
    @PostMapping("/new")
    public ResponseEntity<?> write(@RequestBody WriteDto writeDto) {
        System.out.println("글작성: " + writeDto.toString());
        ResponseEntity<?> result = diaryService.saveWrite(writeDto);
        return result;
    }

    //글 수정하기
    @PostMapping("/rewrite")
    public ResponseEntity<?> rewrite(@RequestBody RewriteDto rewriteDto) {
        System.out.println("글수정: "+rewriteDto.toString());
        ResponseEntity<?> result = diaryService.reWrite(rewriteDto);
        return result;
    }

    //글 삭제하기
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody DeleteDto deleteDto) {
        System.out.println("글삭제: "+deleteDto.toString());
        ResponseEntity<?> result = diaryService.diaryDelete(deleteDto);
        return result;
    }

    //글 보내주기
    @PostMapping("/list")
    public ResponseEntity<?> listedDiary(@RequestBody DiaryListDto diaryListDto) {
        System.out.println("아이디, 년, 월 요청: "+ diaryListDto.toString());
        ResponseEntity<?> result = diaryService.diaryList(diaryListDto);
        return result;
    }

    //글 불러오기(수정하려고)
    @PostMapping("/load")
    public ResponseEntity<?> loadDiary(@RequestBody LoadDto loadDto){
        System.out.println("글id, 어카운트 요청: "+loadDto.toString());
        ResponseEntity<?> result = diaryService.diaryLoad(loadDto.getId(), loadDto.getAccount());
        return result;
    }

    //글 5개 보내주기
    @PostMapping("/myload")
    public ResponseEntity<?> listedFiveDiary(@RequestBody AccountDto account) {
        System.out.println("어카운트: "+account.toString());
        ResponseEntity<?> result = diaryService.diaryListFive(account.getAccount());
        return result;
    }
}
