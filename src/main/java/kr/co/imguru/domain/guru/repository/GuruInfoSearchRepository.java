package kr.co.imguru.domain.guru.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.guru.entity.GuruInfo;
import kr.co.imguru.domain.guru.entity.QGuruInfo;
import kr.co.imguru.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GuruInfoSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QGuruInfo guruInfo = QGuruInfo.guruInfo;

    private final QMember member = QMember.member;

    public Optional<GuruInfo> findGuruInfoByMemberNickname(String memberNickname) {
        GuruInfo temp = queryFactory
                .selectFrom(guruInfo)
                .join(member)
                .on(guruInfo.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        guruInfo.isDelete.eq(Boolean.FALSE),
                        guruInfo.member.nickname.eq(memberNickname)
                )
                .fetchOne();

        return Optional.ofNullable(temp);
    }


}
