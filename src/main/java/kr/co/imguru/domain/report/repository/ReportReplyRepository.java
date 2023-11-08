package kr.co.imguru.domain.report.repository;

import kr.co.imguru.domain.report.entity.ReportReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportReplyRepository extends JpaRepository<ReportReply, Long> {

    boolean existsByReply_IdAndMember_NicknameAndIsDeleteFalse(Long replyId, String memberNickname);

    List<ReportReply> findAllByReply_Id(Long replyId);

    List<ReportReply> findAllByMember_Nickname(String memberNickname);
}
