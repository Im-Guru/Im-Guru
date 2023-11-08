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
            "(msg.sender.id = :sender AND msg.receiver.id = :receiver) OR " +
            "(msg.sender.id = :receiver AND msg.receiver.id = :sender) " +
            "ORDER BY msg.regDate ASC")
    List<Message> findMessagesBetweenMembers(@Param("sender") Long sender, @Param("receiver") Long receiver);// sender와 receiver간에 주고 받은 메세지 목록


}
