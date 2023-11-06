package kr.co.imguru.domain.review.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "member_id")
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guru", referencedColumnName = "member_id")
    private Member guru;

    @Column(name = "content")
    @NotBlank
    private String content;

    @Column(name = "rate")
    @NotBlank
    private int rate;

    @Column(name = "like_cnt")
    private Long likeCnt;

    public Review(Member user,
                  Member guru,
                  String content,
                  int rate,
                  Long likeCnt) {
        this.user = user;
        this.guru = guru;
        this.content = content;
        this.rate = rate;
        this.likeCnt = likeCnt;
    }
}
