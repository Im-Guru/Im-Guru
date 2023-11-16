package kr.co.imguru.domain.post.repository;

import kr.co.imguru.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndIsDeleteFalse(Long id);

    List<Post> findAllByIsDeleteFalse();

    List<Post> findAllByIsGuruAndIsDeleteFalse(boolean isGuru);

    @Query("SELECT COUNT(r) " +
            "FROM Reply r " +
            "WHERE r.post.id = :postId AND r.isDelete = false")
    int countRepliesByPostId(@Param("postId") Long postId);
}

