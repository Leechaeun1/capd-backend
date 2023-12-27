package com.diary.Entity;

import com.diary.dto.SignUpDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="UserEntity")
@Table(name="user")
public class UserEntity {
    @Id
    private String usrId;
    private String usrPw;
    private String usrNickname;
    private String usrEmail;
    private Timestamp usrJoindate;
    private String usrProfile;
    private String usrTheme;

    /*
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<DiaryEntity> diaries;
    */


    public UserEntity(SignUpDto dto) {
        this.usrId = dto.getAccount();
        this.usrPw = dto.getPassword();
        this.usrJoindate = Timestamp.valueOf(LocalDateTime.now().plusHours(9));
        //지금 db 시간이 asiaseoul로 안바뀌어서 utc + 9적용
        this.usrTheme = "basicTheme";
        this.usrProfile = "/profile/de_fa_ul_t.jpg";
    }

    //로그인 할 때 가져올 정보 2개
    public interface LoginMapping{
        String getusrProfile();
        String getusrTheme();
    }
}
