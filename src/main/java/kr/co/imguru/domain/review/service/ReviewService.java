package kr.co.imguru.domain.review.service;

import kr.co.imguru.domain.review.dto.ReviewCreateDto;
import kr.co.imguru.domain.review.dto.ReviewReadDto;
import kr.co.imguru.domain.review.dto.ReviewUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    void createReview(String email, ReviewCreateDto createDto);

//    void createReview(String email, ReviewCreateDto createDto, List<MultipartFile> files) throws IOException;

    ReviewReadDto getReview(Long reviewId);

    List<ReviewReadDto> getReviewsByGuru(String guruNickname);

    List<ReviewReadDto> getReviewsByUser(String userNickname);

    List<ReviewReadDto> getAllReviews();

    ReviewReadDto addLikeReviewByMember(Long reviewId, String email);

    List<ReviewReadDto> getLikeReviewsByMember(String memberNickname);

    ReviewReadDto updateReview(Long reviewId, ReviewUpdateDto updateDto);

    void deleteReview(Long reviewId);

    boolean checkReviewDuplicated(Long payId);

    List<ReviewReadDto> getReviewsByLoginMember(String email);

    List<ReviewReadDto> getReviewsByMemberNickname(String memberNickname);

    List<ReviewReadDto> getGuruReviewsByMemberNickname(String memberNickname);

    List<ReviewReadDto> getGuruReviewsByLoginMember(String email);

}
