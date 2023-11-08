package kr.co.imguru.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageMemberDto {

    private String memberNickname;

    private String memberRole;

    private String memberSkill;
}
