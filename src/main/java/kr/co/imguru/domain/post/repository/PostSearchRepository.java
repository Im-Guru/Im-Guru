package kr.co.imguru.domain.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.imguru.domain.member.entity.QMember;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.post.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QPost post = QPost.post;

    private final QMember member = QMember.member;

    public List<Post> findPostsByMemberNickname(String memberNickname) {
        return queryFactory
                .selectFrom(post)
                .join(member)
                .on(post.member.id.eq(member.id))
                .fetchJoin()
                .where(
                        post.isDelete.eq(Boolean.FALSE),
                        post.member.nickname.eq(memberNickname)
                )
                .fetch();
    }

    private BooleanExpression postSearchText(String searchType, String searchText) {
        if (!StringUtils.hasText(searchText)) {
            return null;
        } else if (searchType.equals("title")) {
            return post.title.contains(searchText);
        } else if (searchType.equals("writer")) {
            return post.member.nickname.contains(searchText);
        } else if (searchType.equals("guru")){
            return post.isDelete.eq(Boolean.TRUE);
        }else {
            return post.title.contains(searchText).or(post.member.nickname.contains(searchText));
        }
    }

}
