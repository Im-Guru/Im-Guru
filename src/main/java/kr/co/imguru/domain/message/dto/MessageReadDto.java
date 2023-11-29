package kr.co.imguru.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageReadDto {

    private Long messageId;

    private String senderNickname;

    private String receiverNickname;

    private String content;

    private LocalDateTime regDate;
}
