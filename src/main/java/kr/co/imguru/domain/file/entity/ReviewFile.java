package kr.co.imguru.domain.file.entity;

import jakarta.persistence.*;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.review.entity.Review;
import kr.co.imguru.global.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ReviewFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_extension")
    private String fileExtension;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "revie_id", nullable = false)
    private Review review;

//    public File(MultipartFile validatedFile, Post post) {
//        this.fileName = validatedFile.getOriginalFilename();
//        this.fileUrl = "http://localhost:3000/api/v1/files/" + post.getId() + "_" + this.fileName;
//        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
//        this.post = post;
//    }

}