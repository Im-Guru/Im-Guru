package kr.co.imguru.domain.report.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.global.common.BaseEntity;
import kr.co.imguru.global.common.ReportCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "report_category")
    @Enumerated(EnumType.STRING)
    private ReportCategory reportCategory;

    @Column(name="description", columnDefinition = "text")
    @NotBlank
    private String description;

    @Column(nullable = false, name="is_accept")
    private boolean isAccept = false;

    public ReportPost(Member member,
                      Post post,
                      ReportCategory reportCategory,
                      String description,
                      boolean isAccept) {
        this.member = member;
        this.post = post;
        this.reportCategory = reportCategory;
        this.description = description;
        this.isAccept = isAccept;
    }
}
