package kr.co.imguru.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.like.entity.LikeReply;
import kr.co.imguru.domain.like.entity.QLikeReply;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.reply.entity.QReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeReplySearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QMember member = QMember.member;

    private final QReply reply = QReply.reply;

    private final QLikeReply likeReply = QLikeReply.likeReply;

    public LikeReply existsByReplyIdAndMemberId(Long replyId, Long memberId) {
        return queryFactory
                .selectFrom(likeReply)
                .join(reply).on(likeReply.reply.id.eq(reply.id))
                .fetchJoin()
                .join(member).on(likeReply.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        likeReply.isDelete.eq(Boolean.FALSE),
                        likeReply.member.id.eq(memberId),
                        likeReply.reply.id.eq(replyId)
                )
                .fetchOne();
    }
}
