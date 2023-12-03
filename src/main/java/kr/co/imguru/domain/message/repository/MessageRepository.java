package kr.co.imguru.domain.message.repository;

import kr.co.imguru.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT CONVERT(contact, SIGNED) " +
            "FROM (" +
            "  SELECT receiver AS contact " +
            "  FROM message " +
            "  WHERE sender = :userId " +
            "  UNION " +
            "  SELECT sender AS contact " +
            "  FROM message " +
            "  WHERE receiver = :userId " +
            ") AS combined_messages", nativeQuery = true)
    List<Long> findMemberMessageList(@Param("userId") Long userId);

    @Query("SELECT msg FROM Message msg WHERE " +
            "(msg.sender.id = :member AND msg.receiver.id = :login) OR " +
            "(msg.sender.id = :login AND msg.receiver.id = :member) " +
            "ORDER BY msg.regDate ASC")
    List<Message> findMessagesBetweenMembers(@Param("login") Long login, @Param("member") Long member);
    // 로그인한 유저와 선택한 유저간의 주고받은 메세지 목록


}
