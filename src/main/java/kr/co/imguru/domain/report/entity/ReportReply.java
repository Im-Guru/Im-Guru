package kr.co.imguru.domain.report.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.reply.entity.Reply;
import kr.co.imguru.global.common.BaseEntity;
import kr.co.imguru.global.common.ReportCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @Column(name = "report_category")
    @Enumerated(EnumType.STRING)
    private ReportCategory reportCategory;

    @Column(name="description", columnDefinition = "text")
    @NotBlank
    private String description;

    @Column(nullable = false, name="is_accept")
    private boolean isAccept = false;

    public ReportReply(Member member,
                       Reply reply,
                       ReportCategory reportCategory,
                       String description,
                       boolean isAccept) {
        this.member = member;
        this.reply = reply;
        this.reportCategory = reportCategory;
        this.description = description;
        this.isAccept = isAccept;
    }

}
