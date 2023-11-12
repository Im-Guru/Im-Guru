package kr.co.imguru.domain.file.entity;

import jakarta.persistence.*;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.review.entity.Review;
import kr.co.imguru.global.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Entity
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_category")
    private String fileCategory;    // Member, Post, Review

    @Column(name = "file_key")
    private Long fileKey;           // Category에 해당하는 Id 값

    public File(MultipartFile validatedFile, Member member) {
        this.fileName = validatedFile.getOriginalFilename();
        this.fileUrl = "http://localhost:3000/api/v1/member/files/" + member.getId() + "_" + this.fileName;
        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        this.fileCategory = "member";
        this.fileKey = member.getId();
    }

    public File(MultipartFile validatedFile, Post post) {
        this.fileName = validatedFile.getOriginalFilename();
        this.fileUrl = "http://localhost:3000/api/v1/post/files/" + post.getId() + "_" + this.fileName;
        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        this.fileCategory = "post";
        this.fileKey = post.getId();
    }

    public File(MultipartFile validatedFile, Review review) {
        this.fileName = validatedFile.getOriginalFilename();
        this.fileUrl = "http://localhost:3000/api/v1/review/files/" + review.getId() + "_" + this.fileName;
        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        this.fileCategory = "review";
        this.fileKey = review.getId();
    }
}
