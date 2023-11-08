package kr.co.imguru.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateDto {

    private String senderNickname;

    private String receiverNickname;

    private String content;
}
