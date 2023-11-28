package kr.co.imguru.domain.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.co.imguru.domain.review.dto.ReviewCreateDto;
import kr.co.imguru.domain.review.dto.ReviewReadDto;
import kr.co.imguru.domain.review.dto.ReviewUpdateDto;
import kr.co.imguru.domain.review.service.ReviewService;
import kr.co.imguru.global.auth.CustomUserDetails;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewRestController {

    private final ReviewService reviewService;

//    @PostMapping("/review")
//    public ResponseFormat<Void> createReview(@RequestBody @Valid ReviewCreateDto createDto) {
//        reviewService.createReview(createDto);
//
//        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
//    }

    @PostMapping(value = "/review", consumes = {"multipart/form-data"})
    public ResponseFormat<Void> createReview(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestPart("createDto") @Valid ReviewCreateDto createDto,
                                             @RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException {
        reviewService.createReview(userDetails.getUsername(), createDto, files);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    /*파일 링크 클릭 시 파일 저장*/
    @GetMapping("/review/files/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName,
                                          HttpServletRequest request) throws IOException {
        /*프로젝트 루트 경로*/
        String rootDir = System.getProperty("user.dir");

        /*file의 path를 저장 -> 클릭 시 파일로 이동*/
        Path filePath = Path.of(rootDir + "/media/review/" + fileName);

        /*파일의 패스를 uri로 변경하고 resource로 저장.*/
        Resource resource = new UrlResource(filePath.toUri());

        /*컨텐츠 타입을 가지고 온다.*/
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

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

    @PostMapping("/review/like/{reviewId}")
    public ResponseFormat<ReviewReadDto> addLikeReview(@AuthenticationPrincipal UserDetails userDetails,
                                                       @PathVariable Long reviewId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reviewService.addLikeReviewByMember(reviewId, userDetails.getUsername()));
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
