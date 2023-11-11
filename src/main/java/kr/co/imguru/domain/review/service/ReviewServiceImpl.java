package kr.co.imguru.domain.review.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.like.LikeReview;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final MemberRepository memberRepository;

    private final LikeReviewRepository likeReviewRepository;

    private final ReviewSearchRepository reviewSearchRepository;

    private final LikeReviewSearchRepository likeReviewSearchRepository;

    @Override
    @Transactional
    public void createReview(ReviewCreateDto createDto) {
        Optional<Member> user = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getUserNickname());
        isMember(user);
        isUser(user);

        Optional<Member> guru = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getGuruNickname());
        isMember(guru);
        isGuru(guru);

        reviewRepository.save(toEntity(createDto));
    }

    @Override
    @Transactional
    public ReviewReadDto getReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findByIdAndIsDeleteFalse(reviewId);

        isReview(review);

        return toReadDto(review.get());
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

    private Review toEntity(ReviewCreateDto dto) {
        return Review.builder()
                .user(memberRepository.findByNicknameAndIsDeleteFalse(dto.getUserNickname()).get())
                .guru(memberRepository.findByNicknameAndIsDeleteFalse(dto.getGuruNickname()).get())
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

}
