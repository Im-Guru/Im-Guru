package kr.co.imguru.domain.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.post.dto.PostUpdateDto;
import kr.co.imguru.global.common.BaseEntity;
import kr.co.imguru.global.common.PostCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "post_category")
    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "content", columnDefinition = "text")
    @NotBlank
    private String content;

    @Column(name = "is_guru")
    private boolean isGuru = false;     // true 면, 도사가 작성한 글

    @Column(name = "price")
    private String price;               // 도사가 작성한 글인 경우만 price 입력 가능

    @Column(name = "like_cnt")
    private Long likeCnt;

    @Column(name = "view_cnt")
    private Long viewCnt;

    @Builder
    public Post(Member member,
                PostCategory postCategory,
                String title,
                String content,
                boolean isGuru,
                String price,
                Long likeCnt,
                Long viewCnt) {
        this.member = member;
        this.postCategory = postCategory;
        this.title = title;
        this.content = content;
        this.isGuru = isGuru;
        this.price = price;
        this.likeCnt = likeCnt;
        this.viewCnt = viewCnt;
    }

    public void changePost(PostUpdateDto updateDto) {
        this.postCategory = PostCategory.valueOf(updateDto.getCategoryName());
        this.title = updateDto.getTitle();
        this.content = updateDto.getContent();
        this.price = updateDto.getPrice();
    }

    public void addViewCnt(Long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public void addLikeCnt() {
        this.likeCnt += 1;
    }

}
