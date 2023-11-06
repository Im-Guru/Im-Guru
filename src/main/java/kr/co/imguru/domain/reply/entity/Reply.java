package kr.co.imguru.domain.reply.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content", columnDefinition = "text")
    @NotBlank
    private String content;

    @Column(name = "like_cnt")
    private Long likeCnt;

    public Reply(Member member,
                 Post post,
                 String content,
                 Long likeCnt) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.likeCnt = likeCnt;
    }
}
