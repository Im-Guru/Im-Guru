package kr.co.imguru.domain.message.service;

import kr.co.imguru.domain.message.dto.MessageCreateDto;
import kr.co.imguru.domain.message.dto.MessageMemberDto;
import kr.co.imguru.domain.message.dto.MessageReadDto;

import java.util.List;

public interface MessageService {

    void createMessage(String email, MessageCreateDto createDto);

    //해당 회원과 메세지 받거나 보낸 회원 리스트
    List<MessageMemberDto> getMessageByMember(String email);

    //리스트에서 클릭 한 해당 회원과 주고 받은 메세지 리스트 조회
    List<MessageReadDto> getDetailMessageByMember(String sender, String receiver);

}
