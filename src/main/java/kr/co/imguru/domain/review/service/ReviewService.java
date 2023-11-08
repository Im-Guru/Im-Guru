package kr.co.imguru.domain.review.service;

import kr.co.imguru.domain.review.dto.ReviewCreateDto;
import kr.co.imguru.domain.review.dto.ReviewReadDto;
import kr.co.imguru.domain.review.dto.ReviewUpdateDto;

import java.util.List;

public interface ReviewService {

    void createReview(ReviewCreateDto createDto);

    ReviewReadDto getReview(Long reviewId);

    List<ReviewReadDto> getReviewsByGuru(String guruNickname);

    List<ReviewReadDto> getReviewsByUser(String userNickname);

    List<ReviewReadDto> getAllReviews();

    ReviewReadDto updateReview(Long reviewId, ReviewUpdateDto updateDto);

    void deleteReview(Long reviewId);

}
