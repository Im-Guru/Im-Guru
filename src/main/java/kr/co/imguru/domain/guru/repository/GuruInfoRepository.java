package kr.co.imguru.domain.guru.repository;

import kr.co.imguru.domain.guru.entity.GuruInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuruInfoRepository extends JpaRepository<GuruInfo, Long> {

    /*
    select *
    from guruInfo g, member m
    where g.member_id = m.id
    and g.is_delete = false
    and m.nickname := ?
     */
    Optional<GuruInfo> findGuruInfoByMember_NicknameAndIsDeleteFalse(String memberNickname);

    List<GuruInfo> findAllByIsDeleteFalse();
}
