package com.diary.Entity;

import com.diary.dto.WriteDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@Getter
@Setter
@Entity(name = "DiaryEntity")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diary_table")
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 일기 id

    @Column(name = "usr_id", length = 20, nullable = false)
    private String usrId;

    @Column(name = "title", length = 50, nullable = true)
    private String title;

    @Column(name = "body", length = 2000)
    private String body;

    @Column(name = "emoji", length = 20)
    private String emoji;

    @Column(name = "summed", length = 255)
    private String summed;

    private String theme;

    private Date createdAt;

    private Timestamp updatedAt;

    // 1은 활성화 0은 삭제(삭제시 0으로바뀜)
    @Column(name = "activate")
    private int activate;

    /* 아직 실용성을 몰라서 keep
    @ManyToOne
    @JoinColumn(name = "usr_id", nullable = false)
    private UserEntity userEntity;
    */
    // id, title, body, emoji, summed, createdAt, updatedAt, activate의 생성자 및 메서드 등 추가 가능

    //생성자 형식으로 받아오기위해서
    public DiaryEntity (WriteDto dto) {
        //id는 자동으로 증가되므로 뺍니다.
        this.usrId = dto.getAccount();
        this.title = dto.getTitle();
        this.body = dto.getBody();
        this.emoji = dto.getEmoji();
        this.summed = dto.getSummed();
        this.theme = dto.getTheme();
        this.createdAt = Date.valueOf(LocalDate.now());
        //UTC시간이라 한국시간에 맞추기 위해 +9시간
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().plusHours(9));
        this.activate = 1;
    }
}
