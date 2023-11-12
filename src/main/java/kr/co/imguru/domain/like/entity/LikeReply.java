package kr.co.imguru.domain.like.entity;

import jakarta.persistence.*;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.reply.entity.Reply;
import kr.co.imguru.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @Builder
    public LikeReply(Member member,
                     Reply reply) {
        this.member = member;
        this.reply = reply;
    }
}
