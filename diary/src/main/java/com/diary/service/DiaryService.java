package com.diary.service;

import com.diary.Entity.DiaryEntity;
import com.diary.Repository.DiaryRepository;
import com.diary.Repository.UserRepository;
import com.diary.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final ConnectAI_Service connectAIService;
    private final ImageService imageService;

    //회원 존재여부 검사도 하기위해서 userRepository를 가져옵니다.
    @Autowired
    public DiaryService(DiaryRepository diaryRepository, UserRepository userRepository, ConnectAI_Service connectAIService, ImageService imageService) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
        this.connectAIService = connectAIService;
        this.imageService = imageService;
    }


    //일기 테마 인공지능으로부터 불러오기
    public ResponseEntity<?> getTheme(GetThemeDto want)
    {
        if(!userRepository.existsById(want.getAccount())){
            return ResponseEntity.status(400).body("잘못된 접근");
        }
        String url = "http://10.50.99.242:1223/compute";
        try{
            String content = want.getBody();
            GetAIDto dto = connectAIService.getTheme(url, content);

            //url 붙여주기
            String imgurl = "http://10.50.99.242:1223";
            String get1 = dto.getUrl1();
            String get2 = dto.getUrl2();
            String get3 = dto.getUrl3();
            String get4 = dto.getUrl4();
            dto.setUrl1(imgurl+get1);
            dto.setUrl2(imgurl+get2);
            dto.setUrl3(imgurl+get3);
            dto.setUrl4(imgurl+get4);

            System.out.println("잘 받았다.: "+dto.toString());

            return ResponseEntity.status(200).body(dto);
        }catch(Exception e){
            return ResponseEntity.status(200).body("받기 실패");
        }
    }

    //유저의 글쓴것 저장하기
    public ResponseEntity<?> saveWrite(WriteDto write)
    {
        //유저 정보확인
        try{
            String account = write.getAccount();
            if(!userRepository.existsById(account))
                return ResponseEntity.status(404).body("존재하지 않는 유저입니다.");
        }catch(Exception e){
            System.out.println("존재아이디 찾던 중 데이터베이스 오류");
            return ResponseEntity.status(400).body("데이터베이스 오류");
        }
        //유저가 존재한다면 글 쓴거 불러오기
        DiaryEntity de = new DiaryEntity(write);
        try{
            //불러온거저장
            DiaryEntity savedEntity = diaryRepository.save(de);

            //id값을 다시 찾아와야함
            Long generatedId = savedEntity.getId();
            String generatedTheme = savedEntity.getTheme();
            //선택된 id, url보내주기
            ReturnInfoDto result = new ReturnInfoDto();
            result.setId(generatedId);
            result.setTheme(generatedTheme);

            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            System.out.println("저장 오류");
            return ResponseEntity.status(400).body("저장 오류");
        }

    }


    //유저의 글 수정하기
    public ResponseEntity<?> reWrite(RewriteDto rewriteDto){
        //유저 존재정보는 안해도됨, 기존거 수정이라서
        //유저 아이디와 글 아이디를통해 글이 존재하는지 확인해야함
        Long id = rewriteDto.getId();
        String account = rewriteDto.getAccount();
        System.out.println("아이디, 계정: "+id + account);
        try {
            Optional<DiaryEntity> de = diaryRepository.findByIdAndUsrId(id, account);
            if(de.isEmpty()){
                System.out.println("찾는 글이 존재하지 않아요");
                return ResponseEntity.status(404).body("찾는 글이 존재하지 않아요");
            }
            DiaryEntity rewriteDiary = de.get();
            rewriteDiary.setTitle(rewriteDto.getTitle());
            rewriteDiary.setBody(rewriteDto.getBody());
            rewriteDiary.setEmoji(rewriteDto.getEmoji());
            rewriteDiary.setSummed(rewriteDto.getSummed());
            rewriteDiary.setTheme(rewriteDto.getTheme());
            //시간은 + 9시간, 서버가 UTC라서, 환경변수 Asia/seoul해도 안되서 어쩔 수 없다
            rewriteDiary.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now().plusHours(9)));
            //성공
            System.out.println("담기는 되나?");
            DiaryEntity diary = diaryRepository.save(rewriteDiary);
            //선택된 id, url보내주기
            ReturnInfoDto result = new ReturnInfoDto();
            result.setId(rewriteDiary.getId());
            result.setTheme(rewriteDiary.getTheme());

            return ResponseEntity.status(200).body(result);
        } catch(Exception e){
            System.out.println("글 찾던 중 데이터베이스 오류이거나 저장오류");
            return ResponseEntity.status(400).body("데이터 베이스 오류");
        }

    }

    //유저의 글 삭제하기
    public ResponseEntity<String> diaryDelete(DeleteDto deleteDto){
        Long id = deleteDto.getId();
        String account = deleteDto.getAccount();
        try{
            Optional<DiaryEntity> de = diaryRepository.findByIdAndUsrId(id, account);
            if(de.isEmpty()){
                System.out.println("없는 일기");
                return ResponseEntity.status(404).body("없는 일기입니다.");
            }
            DiaryEntity wantDelete = de.get();
            //삭제를 의미하는 0으로 만듭니다.
            wantDelete.setActivate(0);
            diaryRepository.save(wantDelete);
            return ResponseEntity.status(200).body("삭제 성공!");
        }catch(Exception e){
            System.out.println("데이터베이스 오류입니다. 데이터베이스 작동중지");
            return ResponseEntity.status(400).body("데이터베이스 오류");
        }
    }

    //유저의 글리스트 불러오기
    public ResponseEntity<?> diaryList(DiaryListDto diaryListDto) {
        try{
            //글 불러오기
            Optional<List<ReturnDiaryDto>> returnDiaryDtoOptional =
                    diaryRepository.findReturnDiaryDtoByUsrIdAndCreatedAtYearAndActivate(diaryListDto.getAccount(), diaryListDto.getX(), 1);
            List<ReturnDiaryDto> result = returnDiaryDtoOptional.get();
            
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            System.out.println("데이터베이스 오류입니다. 데이터베이스가 꺼져잇나확인");
            return ResponseEntity.status(400).body("데이터베이스 오류");
        }
    }


    //유저의 일기내용 불러오기(수정하려고 할 때)
    public ResponseEntity<?> diaryLoad(Long id, String account) {
        try{
            Optional<DiaryEntity> de = diaryRepository.findByIdAndUsrId(id, account);
            if(de.isPresent()){
                LoadDiaryDto w = new LoadDiaryDto();
                w.setTitle(de.get().getTitle());
                w.setBody(de.get().getBody());
                w.setTheme(de.get().getTheme());
                w.setEmoji(de.get().getEmoji());
                w.setUpdatedAt(de.get().getUpdatedAt().toLocalDateTime().toString());
                System.out.println("이거는 보내는 원소: "+w.toString());
                return ResponseEntity.status(200).body(w);
            } else {
                System.out.println("잘못된 접근");
                return ResponseEntity.status(404).body("잘못된 접근");
            }
        } catch (Exception e){
            System.out.println("데이터베이스 오류");
            return ResponseEntity.status(400).body("데이터베이스 오류");
        }
    }


    //유저의 일기 5개 불러주기(최신)
    public ResponseEntity<?> diaryListFive(String account) {
        try{
            Optional<List<ReturnFiveDto>> od = diaryRepository.findTop5ReturnFiveDtoByUsrIdAndActivateOrderByCreatedAtDesc(account, 1);
            if(od.isEmpty())
                System.out.println("리스트가 비어있음");
            System.out.println(od.toString());
            List<ReturnFiveDto> list = od.get();
            return ResponseEntity.status(200).body(list);
        } catch (Exception e) {
            System.out.println("데이터베이스 오류");
            return ResponseEntity.status(400).body("데이터베이스 오류입니다.");
        }
    }
}