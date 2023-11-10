package kr.co.imguru.domain.like.repository;

import kr.co.imguru.domain.like.LikeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {


}

