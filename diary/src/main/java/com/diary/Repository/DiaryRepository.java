package com.diary.Repository;

import com.diary.Entity.DiaryEntity;
import com.diary.dto.ReturnDiaryDto;
import com.diary.dto.ReturnFiveDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    //글 아이디와 유저아이디로 글을 찾고 불러옴.
    Optional<DiaryEntity> findByIdAndUsrId(Long id, String usrId);

    @Query("SELECT d FROM DiaryEntity d WHERE d.usrId = :usrId AND YEAR(d.createdAt) = :x AND d.activate = :activate")
    Optional<List<DiaryEntity>> findByUsrIdAndCreatedAtYearAndActivate(
            @Param("usrId") String usrId,
            @Param("x") int x,
            @Param("activate") int activate
    );

    default Optional<List<ReturnDiaryDto>> findReturnDiaryDtoByUsrIdAndCreatedAtYearAndActivate(
            String usrId, int x, int activate
    ) {
        return findByUsrIdAndCreatedAtYearAndActivate(usrId, x, activate)
                .map(diaryEntities -> diaryEntities.stream()
                        .map(diaryEntity -> new ReturnDiaryDto(diaryEntity.getId(), diaryEntity.getEmoji(), diaryEntity.getCreatedAt().toLocalDate().getMonthValue() ,diaryEntity.getCreatedAt().toLocalDate().getDayOfMonth()))
                        .collect(Collectors.toList())
                );
    }

    //상위 5개의 글 가져오기


    Optional<List<DiaryEntity>> findTop5ByUsrIdAndActivateOrderByCreatedAtDesc(String usrId, int activate);

    default Optional<List<ReturnFiveDto>> findTop5ReturnFiveDtoByUsrIdAndActivateOrderByCreatedAtDesc(
            String usrId, int activate
    ) {
        return findTop5ByUsrIdAndActivateOrderByCreatedAtDesc(usrId, activate)
                .map(diaryEntities -> diaryEntities.stream()
                        .map(diaryEntity -> new ReturnFiveDto(diaryEntity.getTitle(), diaryEntity.getSummed(), diaryEntity.getCreatedAt().toLocalDate().toString(), diaryEntity.getId()))
                        .collect(Collectors.toList())
                );
    }
}
