package kr.co.imguru.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.review.entity.QReview;
import kr.co.imguru.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewSearchRepository {

    private final JPAQueryFactory queryFactory;

    private QReview review = QReview.review;

    private QMember member = QMember.member;

    public List<Review> findReviewsByUserNickname(String userNickname) {
        return queryFactory
                .selectFrom(review)
                .join(member)
                .on(review.user.id.eq(member.id))
                .fetchJoin()
                .where(
                        review.isDelete.eq(Boolean.FALSE),
                        review.user.nickname.eq(userNickname)
                )
                .fetch();
    }

    public List<Review> findReviewsByGuruNickname(String guruNickname) {
        return queryFactory
                .selectFrom(review)
                .join(member)
                .on(review.guru.id.eq(member.id))
                .fetchJoin()
                .where(
                        review.isDelete.eq(Boolean.FALSE),
                        review.guru.nickname.eq(guruNickname)
                )
                .fetch();
    }


}
