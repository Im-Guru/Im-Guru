package kr.co.imguru.domain.like.repository;

import kr.co.imguru.domain.like.LikeReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeReplyRepository extends JpaRepository<LikeReply, Long> {

}
