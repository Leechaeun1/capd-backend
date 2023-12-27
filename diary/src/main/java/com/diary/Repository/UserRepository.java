package com.diary.Repository;

import com.diary.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//<entity, primarykey type>
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    //아이디랑 비밀번호가 일치하는 유저가 존재하는지 확인
    boolean existsByUsrIdAndUsrPw(String usrId, String usrPw);

    Optional<UserEntity> findByUsrId(String usrId);
}
