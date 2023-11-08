package kr.co.imguru.domain.report.repository;

import kr.co.imguru.domain.report.entity.ReportPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost, Long> {

    boolean existsByPost_IdAndMember_NicknameAndIsDeleteFalse(Long postId, String memberNickname);

    List<ReportPost> findAllByPost_Id(Long postId);

    List<ReportPost> findAllByMember_Nickname(String memberNickname);
}
