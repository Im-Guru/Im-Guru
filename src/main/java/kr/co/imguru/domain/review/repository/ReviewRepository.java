package kr.co.imguru.domain.review.repository;

import kr.co.imguru.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByIdAndIsDeleteFalse(Long id);

    List<Review> findAllByIsDeleteFalse();
}
