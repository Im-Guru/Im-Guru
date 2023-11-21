package kr.co.imguru.domain.file.entity;

import jakarta.persistence.*;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.review.entity.Review;
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
        String tempName = member.getId() + "_" + validatedFile.getOriginalFilename();
        tempName = tempName.replaceAll("\\s", "_"); // 공백을 언더스코어로 대체
        tempName = tempName.replaceAll("[^a-zA-Z0-9_.]", ""); // 영문자, 숫자, 언더스코어, 마침표 이외의 문자 제거

        this.fileName = validatedFile.getOriginalFilename();
        this.fileUrl = "http://localhost:3000/api/v1/member/files/" + tempName;

//        this.fileName = validatedFile.getOriginalFilename();
//        this.fileUrl = "http://localhost:3000/api/v1/member/files/" + member.getId() + "_" + this.fileName;
        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        this.fileCategory = "member";
        this.fileKey = member.getId();
    }

    public File(MultipartFile validatedFile, Post post) {
        String tempName = post.getId() + "_" + validatedFile.getOriginalFilename();
        tempName = tempName.replaceAll("\\s", "_"); // 공백을 언더스코어로 대체
        tempName = tempName.replaceAll("[^a-zA-Z0-9_.]", ""); // 영문자, 숫자, 언더스코어, 마침표 이외의 문자 제거

        this.fileName = validatedFile.getOriginalFilename();
        this.fileUrl = "http://localhost:3000/api/v1/post/files/" + tempName;

//        this.fileName = validatedFile.getOriginalFilename();
//        this.fileUrl = "http://localhost:3000/api/v1/post/files/" + post.getId() + "_" + this.fileName;
        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        this.fileCategory = "post";
        this.fileKey = post.getId();
    }

    public File(MultipartFile validatedFile, Review review) {
        String tempName = review.getId() + "_" + validatedFile.getOriginalFilename();
        tempName = tempName.replaceAll("\\s", "_"); // 공백을 언더스코어로 대체
        tempName = tempName.replaceAll("[^a-zA-Z0-9_.]", ""); // 영문자, 숫자, 언더스코어, 마침표 이외의 문자 제거

        this.fileName = validatedFile.getOriginalFilename();
        this.fileUrl = "http://localhost:3000/api/v1/review/files/" + tempName;

//        this.fileName = validatedFile.getOriginalFilename();
//        this.fileUrl = "http://localhost:3000/api/v1/review/files/" + review.getId() + "_" + this.fileName;
        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        this.fileCategory = "review";
        this.fileKey = review.getId();
    }
}
