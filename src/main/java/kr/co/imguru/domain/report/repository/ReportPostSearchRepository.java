package kr.co.imguru.domain.report.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.post.entity.QPost;
import kr.co.imguru.domain.report.entity.QReportPost;
import kr.co.imguru.domain.report.entity.ReportPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportPostSearchRepository {

    private final JPAQueryFactory queryFactory;

    private QReportPost reportPost = QReportPost.reportPost;

    private QMember member = QMember.member;

    private QPost post = QPost.post;

    public List<ReportPost> findReportPostsByMemberNickname(String memberNickname) {
        return queryFactory
                .selectFrom(reportPost)
                .join(member)
                .on(reportPost.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        reportPost.isDelete.eq(Boolean.FALSE),
                        reportPost.member.nickname.eq(memberNickname)
                )
                .fetch();
    }

    public List<ReportPost> findReportPostsByPostId(Long postId) {
        return queryFactory
                .selectFrom(reportPost)
                .join(post)
                .on(reportPost.post.id.eq(post.id))
                .fetchJoin()
                .where(
                        reportPost.isDelete.eq(Boolean.FALSE),
                        reportPost.post.id.eq(postId)
                )
                .fetch();
    }

    public ReportPost existsByPostIdAndMemberNickname(Long postId, String memberNickname) {
        return queryFactory
                .selectFrom(reportPost)
                .join(member)
                .on(reportPost.member.id.eq(member.id))
                .fetchJoin()
                .join(post)
                .on(reportPost.post.id.eq(post.id))
                .fetchJoin()
                .where(
                        reportPost.isDelete.eq(Boolean.FALSE),
                        reportPost.member.nickname.eq(memberNickname),
                        reportPost.post.id.eq(postId)
                )
                .fetchOne();
    }

}
