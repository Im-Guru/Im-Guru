package kr.co.imguru.domain.report.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.reply.entity.QReply;
import kr.co.imguru.domain.report.entity.QReportReply;
import kr.co.imguru.domain.report.entity.ReportReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportReplySearchRepository {

    private final JPAQueryFactory queryFactory;

    private QReportReply reportReply = QReportReply.reportReply;

    private QMember member = QMember.member;

    private QReply reply = QReply.reply;

    public List<ReportReply> findReportRepliesByMemberNickname(String memberNickname) {
        return queryFactory
                .selectFrom(reportReply)
                .join(member).on(reportReply.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        reportReply.isDelete.eq(Boolean.FALSE),
                        reportReply.member.nickname.eq(memberNickname)
                )
                .fetch();
    }

    public List<ReportReply> findReportRepliesByReplyId(Long replyId) {
        return queryFactory
                .selectFrom(reportReply)
                .join(reply).on(reportReply.reply.id.eq(reply.id))
                .fetchJoin()
                .where(
                        reportReply.isDelete.eq(Boolean.FALSE),
                        reportReply.reply.id.eq(replyId)
                )
                .fetch();
    }

    public List<ReportReply> findReportRepliesByReply(Long replyId) {
        return queryFactory
                .selectFrom(reportReply)
                .join(reply).on(reportReply.reply.id.eq(reply.id))
                .fetchJoin()
                .where(
                        reportReply.reply.id.eq(replyId)
                )
                .fetch();
    }

    public ReportReply existsByReplyIdAndMemberNickname(Long replyId, String memberNickname) {
        return queryFactory
                .selectFrom(reportReply)
                .join(member).on(reportReply.member.id.eq(member.id))
                .fetchJoin()
                .join(reply).on(reportReply.reply.id.eq(reply.id))
                .fetchJoin()
                .where(
                        reportReply.isDelete.eq(Boolean.FALSE),
                        reportReply.member.nickname.eq(memberNickname),
                        reportReply.reply.id.eq(replyId)
                )
                .fetchOne();
    }
}
