package kr.co.imguru.domain.like.repository;

import kr.co.imguru.domain.like.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {


}
