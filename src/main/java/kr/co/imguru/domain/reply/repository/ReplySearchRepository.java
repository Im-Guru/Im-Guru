package kr.co.imguru.domain.reply.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.like.entity.QLikeReply;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.post.entity.QPost;
import kr.co.imguru.domain.reply.entity.QReply;
import kr.co.imguru.domain.reply.entity.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplySearchRepository {

    private final JPAQueryFactory queryFactory;

    private QReply reply = QReply.reply;

    private QMember member = QMember.member;

    private QPost post = QPost.post;

    private QLikeReply likeReply = QLikeReply.likeReply;

    public List<Reply> findRepliesByMemberNickname(String memberNickname) {
        return queryFactory
                .selectFrom(reply)
                .join(member).on(reply.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        reply.isDelete.eq(Boolean.FALSE),
                        reply.member.nickname.eq(memberNickname)
                )
                .fetch();
    }

    public List<Reply> findRepliesByPostId(Long postId) {
        return queryFactory
                .selectFrom(reply)
                .join(post).on(reply.post.id.eq(post.id))
                .fetchJoin()
                .where(
                        reply.isDelete.eq(Boolean.FALSE),
                        reply.post.id.eq(postId)
                )
                .fetch();
    }

    public List<Reply> findRepliesByMember(Long memberId) {
        return queryFactory
                .selectFrom(reply)
                .join(member).on(reply.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        reply.member.id.eq(memberId)
                )
                .fetch();
    }

    public List<Reply> findRepliesByPost(Long postId) {
        return queryFactory
                .selectFrom(reply)
                .join(post).on(reply.post.id.eq(post.id))
                .fetchJoin()
                .where(
                        reply.post.id.eq(postId)
                )
                .fetch();
    }

    public List<Reply> findLikeRepliesByMemberNickname(String memberNickname) {
        return queryFactory
                .selectFrom(reply)
                .join(member).on(reply.member.id.eq(member.id))
                .fetchJoin()
                .join(likeReply).on(likeReply.reply.id.eq(reply.id))
                .fetchJoin()
                .where(
                        reply.isDelete.eq(Boolean.FALSE),
                        likeReply.member.nickname.eq(memberNickname)
                )
                .fetch();
    }


}
