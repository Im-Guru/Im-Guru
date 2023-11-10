package kr.co.imguru.domain.review.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.review.dto.ReviewCreateDto;
import kr.co.imguru.domain.review.dto.ReviewReadDto;
import kr.co.imguru.domain.review.dto.ReviewUpdateDto;
import kr.co.imguru.domain.review.service.ReviewService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewRestController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseFormat<Void> createReview(@RequestBody @Valid ReviewCreateDto createDto) {
        reviewService.createReview(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseFormat<ReviewReadDto> readReview(@PathVariable Long reviewId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reviewService.getReview(reviewId));
    }

    @GetMapping("/review/guru/{guruNickname}")
    public ResponseFormat<List<ReviewReadDto>> readGuruReviews(@PathVariable String guruNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reviewService.getReviewsByGuru(guruNickname));
    }

    @GetMapping("/review/user/{userNickname}")
    public ResponseFormat<List<ReviewReadDto>> readUserReviews(@PathVariable String userNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reviewService.getReviewsByUser(userNickname));
    }

    @GetMapping("/review/all")
    public ResponseFormat<List<ReviewReadDto>> readAllReviews() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reviewService.getAllReviews());
    }

    @PostMapping("/review/like/{reviewId}/{memberNickname}")
    public ResponseFormat<ReviewReadDto> addLikeReview(@PathVariable Long reviewId,
                                                     @PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reviewService.addLikeReviewByMemberNickname(reviewId, memberNickname));
    }

    @GetMapping("/review/like/{memberNickname}")
    public ResponseFormat<List<ReviewReadDto>> readLikeReviewsByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reviewService.getLikeReviewsByMember(memberNickname));
    }

    @PutMapping("/review/{reviewId}")
    public ResponseFormat<ReviewReadDto> updateReview(@PathVariable Long reviewId,
                                                      @RequestBody @Valid ReviewUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reviewService.updateReview(reviewId, updateDto));
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseFormat<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }
}
