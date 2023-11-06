package kr.co.imguru.domain.guru.entity;


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
public class GuruInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guru_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "intro", columnDefinition = "TEXT")
    @NotBlank
    private String intro;           //자기 소개

    @Column(name = "company_name")
    @NotBlank
    private String companyName;     // 회사 소개

    @Column(name = "position")
    @NotBlank
    private String position;        // 회사 직급

    @Column(name = "career_at", columnDefinition = "TEXT")
    @NotBlank
    private String careerAt;        // 회사 경력

    @Column(name = "contact_time")
    @NotBlank
    private String contactTime;     // 연락 가능 시간

    @Column(name = "work_area")
    @NotBlank
    private String workArea;        // 활동 가능 지역

    @Column(name = "description", columnDefinition = "TEXT")
    @NotBlank
    private String description;     // 업무 설명

    public GuruInfo(Member member,
                    String intro,
                    String companyName,
                    String position,
                    String careerAt,
                    String contactTime,
                    String workArea,
                    String description) {
        this.member = member;
        this.intro = intro;
        this.companyName = companyName;
        this.position = position;
        this.careerAt = careerAt;
        this.contactTime = contactTime;
        this.workArea = workArea;
        this.description = description;
    }
}
