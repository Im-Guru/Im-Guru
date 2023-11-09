package kr.co.imguru.domain.reply.repository;

import kr.co.imguru.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<Reply> findByIdAndIsDeleteFalse(Long id);

    List<Reply> findAllByIsDeleteFalse();

}
