package com.diary.service;

import com.diary.Entity.UserEntity;
import com.diary.Repository.UserRepository;
import com.diary.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
//UserController 에 대한 기능 구현

@Service
public class UserService {
    private final ResourceLoader resourceLoader;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, ResourceLoader resourceLoader) {
        this.userRepository = userRepository;
        this.resourceLoader = resourceLoader;
    }

    @Value("${custom.file.path.profile}")
    private String profilePath;
    //회원가입
    public ResponseEntity<?> signUp(SignUpDto dto) {
        //id 중복확인
        String inputid = dto.getAccount();
        try{
            if(userRepository.existsById(inputid))
                return ResponseEntity.status(404).body("이미 가입한 아이디입니다");
        }catch(Exception e){
            return ResponseEntity.status(400).body("데이터 베이스 오류");
        }

        //UserEntity 생성
        UserEntity userEntity = new UserEntity(dto);

        //UserRepository를 이용해서 데이터베이스에 Entity 저장
        try{
            userRepository.save(userEntity);
        } catch (Exception e){
            return ResponseEntity.status(400).body("데이터베이스 오류");
        }

        //가입 성공!
        return ResponseEntity.status(200).body("가입 성공!");
    }

    //로그인 서비스
    public ResponseEntity<SignInResponseDto> logIn(LoginDto dto){
        String userId = dto.getAccount(); //입력받은 아이디
        String userPw = dto.getPassword(); //입력받은 비밀번호
        String message;
        //Id Pw가 일치하는게 있다면 existUser = true
        boolean existUser = userRepository.existsByUsrIdAndUsrPw(userId, userPw);

        try {
            if(!existUser) {
                String auth = null;
                message = "존재하지 않는 아이디나 비밀번호";
                SignInResponseDto signInResponseDto = new SignInResponseDto(auth, message);
                return ResponseEntity.status(404).body(signInResponseDto);
            }
        }catch (Exception error){
            String auth = null;
            message = "데이터베이스 오류";
            SignInResponseDto signInResponseDto = new SignInResponseDto(auth, message);
            return ResponseEntity.status(400).body(signInResponseDto);
        }

        try {
            UserEntity userEntity = userRepository.findById(userId).get();

            userEntity.setUsrPw("");
            String auth = "success";
            message = "로그인 성공";
            //int exprTime = 3600000;

            SignInResponseDto signInResponseDto = new SignInResponseDto(auth, message);
            return ResponseEntity.status(200).body(signInResponseDto);
        }catch (Exception error){
            String auth = null;
            message = "데이터베이스 오류";
            SignInResponseDto signInResponseDto = new SignInResponseDto(auth, message);
            return ResponseEntity.status(400).body(signInResponseDto);
        }

    }

    //로그인시 정보 가져오기
    public ResponseEntity<LoginAfDto> logInAf(String id)
    {
        //있는 아이디인지 체크
        boolean bo = userRepository.existsById(id);
        if(!bo){
            LoginAfDto res = new LoginAfDto();
            return ResponseEntity.status(404).body(res);
        }
        
        try{
            //정보 가져오기(유저 테마, 프로필)
            Optional<UserEntity> userEntity = userRepository.findById(id);
            LoginAfDto res = new LoginAfDto(userEntity);

            //보내주기
            return ResponseEntity.status(200).body(res);
        }catch(Exception error){
            LoginAfDto rest = new LoginAfDto();
            return ResponseEntity.status(400).body(rest);
        }
    }

    //비밀번호 변경
    public ResponseEntity<String> changePw(String account, String newPw){
        System.out.println(account+", "+newPw);
        Optional<UserEntity> userOptional = userRepository.findByUsrId(account);
        try{
            if(userOptional.isPresent()){
                //사용자가 있다면 저장
                UserEntity user = userOptional.get();
                user.setUsrPw(newPw);
                userRepository.save(user);
                //리스폰스 리턴
                return ResponseEntity.status(200).body("비밀번호 변경 성공!");
            } else {
                return ResponseEntity.status(404).body("없는 유저입니다");
            }
        }catch(Exception error){
            return ResponseEntity.status(400).body("데이터베이스 오류");
        }
    }

    //테마 변경
    public ResponseEntity<String> changeTheme(String account, String theme)
    {
        Optional<UserEntity> userOptional = userRepository.findByUsrId(account);
        try{
            if(userOptional.isPresent())
            {
                UserEntity user = userOptional.get();
                user.setUsrTheme(theme);
                userRepository.save(user);
                return ResponseEntity.status(200).body("테마 변경 성공!");
            }else{
                return ResponseEntity.status(404).body("유저가 존재하지 않음");
            }
        }catch(Exception error){
            return ResponseEntity.status(400).body("데이터베이스 오류");
        }
    }



    //프로필 업로드
    public ResponseEntity<ResponseDto> uploadUsrImage(String account, MultipartFile file)
    {

        System.out.println(file.toString());
        try{
            //파일 저장하기
            Resource resource = resourceLoader.getResource("file:"+profilePath+account+".jpg");
            if(!resource.exists())
            {
                File newF = new File("file:"+profilePath+account+".jpg");
                resource = resourceLoader.getResource("file:"+profilePath+account+".jpg");
            }
            File newFile = resource.getFile();
            Files.copy(file.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            /*//아이디 존재유무 체크(디비호출많아져서 그냥 뺌)
            if(!userRepository.existsById(account)){
                return ResponseEntity.status(404).body("없는 아이디");
            }*/
            //디비에 프로필 url갱신 (디폴트일 경우를 위해)
            try{
                Optional<UserEntity> user = userRepository.findByUsrId(account);
                UserEntity usr = user.get();
                usr.setUsrProfile("/profile/"+account+".jpg");
                userRepository.save(usr);
            }catch(Exception e){
                ResponseDto dto = new ResponseDto("/profile/de_fa_ul_t.jpg", "데이터베이스 오류");
                return ResponseEntity.status(400).body(dto);
            }
            ResponseDto dto = new ResponseDto("/profile/"+account+".jpg", "변경 성공!");
            return ResponseEntity.status(200).body(dto);
        } catch (Exception e){
            //업로드 실패
            ResponseDto dto = new ResponseDto("profile/de_fa_ul_t.jpg", "업로드 실패!");
            return ResponseEntity.status(404).body(dto);
        }

    }
}
