package kr.co.imguru.domain.pay.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.pay.entity.Pay;
import kr.co.imguru.domain.pay.entity.QPay;
import kr.co.imguru.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaySearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QMember member = QMember.member;

    private final QPay pay = QPay.pay;

    public List<Pay> findPaysByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(pay)
                .join(member).on(pay.mercntParam2.eq(member.id.stringValue()))
                .fetchJoin()
                .where(
                        pay.mercntParam2.eq(String.valueOf(memberId))
                )
                .fetch();
    }

}
