package kr.co.imguru.domain.file.entity;

import jakarta.persistence.*;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.review.entity.Review;
import lombok.Builder;
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

    // 필요한 것 fileUrl, fileCategory, fileKey
    public File(String fileUrl, String fileCategory, Long fileKey, String fileName) {
        this.fileUrl = fileUrl;
        this.fileCategory = fileCategory;
        this.fileKey = fileKey;
        this.fileName = fileName;
    }

}
