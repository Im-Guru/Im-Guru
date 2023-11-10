package kr.co.imguru.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.like.LikeReply;
import kr.co.imguru.domain.like.LikeReview;
import kr.co.imguru.domain.like.QLikeReview;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.review.entity.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeReviewSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QMember member = QMember.member;

    private final QReview review = QReview.review;

    private final QLikeReview likeReview = QLikeReview.likeReview;

    public LikeReview existsByReviewIdAndMemberId(Long reviewId, Long memberId) {
        return queryFactory
                .selectFrom(likeReview)
                .join(review).on(likeReview.review.id.eq(review.id))
                .fetchJoin()
                .join(member).on(likeReview.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        likeReview.isDelete.eq(Boolean.FALSE),
                        likeReview.member.id.eq(memberId),
                        likeReview.review.id.eq(reviewId)
                )
                .fetchOne();
    }
}
