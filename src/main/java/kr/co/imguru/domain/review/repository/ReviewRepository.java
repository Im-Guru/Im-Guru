package kr.co.imguru.domain.review.repository;

import kr.co.imguru.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByIdAndIsDeleteFalse(Long id);

    List<Review> findAllByIsDeleteFalse();

    List<Review> findAllByUser_NicknameAndIsDeleteFalse(String userNickname);

    List<Review> findAllByGuru_NicknameAndIsDeleteFalse(String guruNickname);
}
