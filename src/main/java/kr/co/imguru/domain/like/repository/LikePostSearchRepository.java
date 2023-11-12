package kr.co.imguru.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.like.entity.LikePost;
import kr.co.imguru.domain.like.entity.QLikePost;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.post.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikePostSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QPost post = QPost.post;

    private final QMember member = QMember.member;

    private final QLikePost likePost = QLikePost.likePost;

    public LikePost existsByPostIdAndMemberId(Long postId, Long memberId) {
        return queryFactory
                .selectFrom(likePost)
                .join(post).on(likePost.post.id.eq(post.id))
                .fetchJoin()
                .join(member).on(likePost.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        likePost.isDelete.eq(Boolean.FALSE),
                        likePost.member.id.eq(memberId),
                        likePost.post.id.eq(postId)
                )
                .fetchOne();
    }
}
