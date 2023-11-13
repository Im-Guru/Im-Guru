package kr.co.imguru.domain.review.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.file.entity.File;
import kr.co.imguru.domain.file.entity.FileFormat;
import kr.co.imguru.domain.file.repository.FileRepository;
import kr.co.imguru.domain.like.entity.LikeReview;
import kr.co.imguru.domain.like.repository.LikeReviewRepository;
import kr.co.imguru.domain.like.repository.LikeReviewSearchRepository;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.review.dto.ReviewCreateDto;
import kr.co.imguru.domain.review.dto.ReviewReadDto;
import kr.co.imguru.domain.review.dto.ReviewUpdateDto;
import kr.co.imguru.domain.review.entity.Review;
import kr.co.imguru.domain.review.repository.ReviewRepository;
import kr.co.imguru.domain.review.repository.ReviewSearchRepository;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.ForbiddenException;
import kr.co.imguru.global.exception.InvalidRequestException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final MemberRepository memberRepository;

    private final LikeReviewRepository likeReviewRepository;

    private final FileRepository fileRepository;

    private final ReviewSearchRepository reviewSearchRepository;

    private final LikeReviewSearchRepository likeReviewSearchRepository;

//    @Override
//    @Transactional
//    public void createReview(ReviewCreateDto createDto) {
//        Optional<Member> user = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getUserNickname());
//        isMember(user);
//        isUser(user);
//
//        Optional<Member> guru = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getGuruNickname());
//        isMember(guru);
//        isGuru(guru);
//
//        reviewRepository.save(toEntity(createDto));
//    }

    @Override
    @Transactional
    public void createReview(ReviewCreateDto createDto, List<MultipartFile> files) throws IOException {
        Optional<Member> user = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getUserNickname());
        isMember(user);
        isUser(user);

        Optional<Member> guru = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getGuruNickname());
        isMember(guru);
        isGuru(guru);

        Review review = toEntity(createDto, user.get(), guru.get());

        reviewRepository.save(review);

        // 파일 저장
        if (files != null && !files.isEmpty()) {
            /* 지원하지 않는 확장자 파일 제거 */
            List<MultipartFile> validatedFiles = filesValidation(files);

            /* 걸러진 파일들 업로드 */
            filesUpload(validatedFiles, review.getId());

            /* 유효성 검증을 끝낸 파일들을 하나씩 꺼냄. */
            for (MultipartFile validatedFile : validatedFiles) {
                /* File Entity 생성 후 저장 */
                File file = new File(validatedFile, review);

                fileRepository.save(file);
            }
        }
    }

    @Override
    @Transactional
    public ReviewReadDto getReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findByIdAndIsDeleteFalse(reviewId);

        isReview(review);

        /*File*/
        List<File> fileList = fileRepository.findFileByFileKey("review", reviewId);
        List<FileFormat> fileFormatList = new ArrayList<>();

        /*파일이 존재한다면*/
        if (fileList != null) {
            for (File file : fileList) {
                FileFormat fileFormat = new FileFormat(file);
                fileFormatList.add(fileFormat);
            }
        }

        return toReadDetailDto(review.get(), fileFormatList);
    }

    @Override
    @Transactional
    public List<ReviewReadDto> getReviewsByGuru(String guruNickname) {
        return reviewSearchRepository.findReviewsByGuruNickname(guruNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ReviewReadDto> getReviewsByUser(String userNickname) {
        return reviewSearchRepository.findReviewsByUserNickname(userNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ReviewReadDto> getAllReviews() {
        return reviewRepository.findAllByIsDeleteFalse()
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public ReviewReadDto addLikeReviewByMemberNickname(Long reviewId, String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);
        isMember(member);

        Optional<Review> review = reviewRepository.findByIdAndIsDeleteFalse(reviewId);
        isReview(review);

        isLikeReviewDuplicated(reviewId, member.get().getId());

        LikeReview create = LikeReview.builder()
                .member(member.get())
                .review(review.get())
                .build();

        likeReviewRepository.save(create);

        review.get().addLikeCnt();

        reviewRepository.save(review.get());

        return toReadDto(review.get());
    }

    @Override
    @Transactional
    public List<ReviewReadDto> getLikeReviewsByMember(String memberNickname) {
        return reviewSearchRepository.findLikeReviewsByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public ReviewReadDto updateReview(Long reviewId, ReviewUpdateDto updateDto) {
        Optional<Review> review = reviewRepository.findByIdAndIsDeleteFalse(reviewId);
        isReview(review);

        Optional<Member> user = memberRepository.findByNicknameAndIsDeleteFalse(updateDto.getUserNickname());
        isMember(user);
        isUser(user);

        isWriter(user, review);

        review.get().changeReview(updateDto);

        reviewRepository.save(review.get());

        return toReadDto(review.get());
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findByIdAndIsDeleteFalse(reviewId);

        isReview(review);

        review.get().changeDeleteAt();

        reviewRepository.save(review.get());
    }

    /*파일의 유효성 검증*/
    private List<MultipartFile> filesValidation(List<MultipartFile> files) throws IOException {
        /*접근 거부 파일 확장자명*/
        String[] accessDeniedFileExtension = {"exe", "zip"};
        /*접근 거부 파일 컨텐츠 타입*/
        String[] accessDeniedFileContentType = {"application/x-msdos-program", "application/zip"};

        ArrayList<MultipartFile> validatedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            /*원본 파일 이름*/
            String originalFileName = file.getOriginalFilename();
            /*파일의 확장자명*/
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            /*파일의 컨텐츠타입*/
            String fileContentType = file.getContentType();

            /*accessDeniedFileExtension, accessDeniedFileContentType -> 업로드 불가*/
            if (Arrays.asList(accessDeniedFileExtension).contains(fileExtension) ||
                    Arrays.asList(accessDeniedFileContentType).contains(fileContentType)) {
                log.warn(fileExtension + "(" + fileContentType + ") 파일은 지원하지 않는 확장자입니다.");
            } else {/*업로드 가능*/
                validatedFiles.add(file);
            }
        }
        return validatedFiles;
    }

    /*파일 업로드 메소드*/
    private void filesUpload(List<MultipartFile> files, Long reviewId) throws IOException {
        /*프로젝트 루트 경로*/
        String rootDir = System.getProperty("user.dir");

        for (MultipartFile file : files) {
            /* 파일 이름 생성 및 수정 */
            String fileName = reviewId + "_" + file.getOriginalFilename();
            fileName = fileName.replaceAll("\\s", "_"); // 공백을 언더스코어로 대체
            fileName = fileName.replaceAll("[^a-zA-Z0-9_.]", ""); // 영문자, 숫자, 언더스코어, 마침표 이외의 문자 제거

            /* 업로드 경로 */
            java.io.File uploadPath = new java.io.File(rootDir + "/media/review/");
            uploadPath.mkdirs(); // 디렉토리가 존재하지 않으면 생성

            uploadPath = new java.io.File(uploadPath, fileName); // 파일 이름을 포함한 전체 경로

            /* 업로드 */
            file.transferTo(uploadPath);
        }
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isReview(Optional<Review> review) {
        if (review.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REVIEW_NOT_FOUND);
        }
    }

    private void isUser(Optional<Member> member) {
        if(!member.get().getRole().getUserRole().equals("회원")) {
            throw new InvalidRequestException(ResponseStatus.FAIL_REVIEW_WRITE_ONLY_USER);
        }
    }

    private void isGuru(Optional<Member> member) {
        if(!member.get().getRole().getUserRole().equals("도사")) {
            throw new InvalidRequestException(ResponseStatus.FAIL_REVIEW_RATE_ONLY_GURU);
        }
    }

    private void isWriter(Optional<Member> member, Optional<Review> review) {
        if (!member.get().getNickname().equals(review.get().getUser().getNickname())) {
            throw new ForbiddenException(ResponseStatus.FAIL_REVIEW_WRITER_NOT_MATCH);
        }
    }

    private void isLikeReviewDuplicated(Long reviewId, Long memberId) {
        Optional<LikeReview> likeReview = Optional.ofNullable(likeReviewSearchRepository.existsByReviewIdAndMemberId(reviewId, memberId));

        if (likeReview.isPresent()) {
            throw new DuplicatedException(ResponseStatus.FAIL_REVIEW_LIKE_DUPLICATED);
        }
    }

    private Review toEntity(ReviewCreateDto dto, Member user, Member guru) {
        return Review.builder()
                .user(user)
                .guru(guru)
                .content(dto.getContent())
                .rate(dto.getRate())
                .likeCnt(0L)
                .build();
    }

    private ReviewReadDto toReadDto(Review review) {
        return ReviewReadDto.builder()
                .userNickname(review.getUser().getNickname())
                .guruNickname(review.getGuru().getNickname())
                .guruSkill(review.getGuru().getSkill().getName())
                .content(review.getContent())
                .rate(review.getRate())
                .likeCnt(review.getLikeCnt())
                .build();
    }

    private ReviewReadDto toReadDetailDto(Review review, List<FileFormat> fileFormatList) {
        return ReviewReadDto.builder()
                .userNickname(review.getUser().getNickname())
                .guruNickname(review.getGuru().getNickname())
                .guruSkill(review.getGuru().getSkill().getName())
                .content(review.getContent())
                .fileFormat(fileFormatList)
                .rate(review.getRate())
                .likeCnt(review.getLikeCnt())
                .build();
    }

}
