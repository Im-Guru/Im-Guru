package kr.co.imguru.domain.report.repository;

import kr.co.imguru.domain.report.entity.ReportReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportReplyRepository extends JpaRepository<ReportReply, Long> {

}
