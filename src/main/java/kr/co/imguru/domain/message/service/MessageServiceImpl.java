package kr.co.imguru.domain.message.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.message.dto.MessageCreateDto;
import kr.co.imguru.domain.message.dto.MessageMemberDto;
import kr.co.imguru.domain.message.dto.MessageReadDto;
import kr.co.imguru.domain.message.entity.Message;
import kr.co.imguru.domain.message.repository.MessageRepository;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void createMessage(MessageCreateDto createDto) {
        Optional<Member> sender = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getSenderNickname());
        isMember(sender);

        Optional<Member> receiver = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getReceiverNickname());
        isMember(receiver);

        messageRepository.save(toEntity(createDto));
    }

    //해당 회원과 메세지 받거나 보낸 회원 리스트
    @Override
    public List<MessageMemberDto> getMessageByMember(String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);
        isMember(member);

        List<Long> tempList = messageRepository.findMemberMessageList(member.get().getId());

        List<MessageMemberDto> memberList = new ArrayList<>();

        for (int i = 0; i < tempList.size(); i++) {
            memberList.add(toMsgMemberDto(memberRepository.findByIdAndIsDeleteFalse(tempList.get(i)).get()));
        }

        return memberList;
    }

    //리스트에서 클릭 한 해당 회원과 주고 받은 메세지 리스트 조회
    @Override
    public List<MessageReadDto> getDetailMessageByMember(String sender, String receiver) {
        Optional<Member> senderMember = memberRepository.findByNicknameAndIsDeleteFalse(sender);
        isMember(senderMember);

        Optional<Member> receiverMember = memberRepository.findByNicknameAndIsDeleteFalse(receiver);
        isMember(receiverMember);

//        List<Message> tempList = messageRepository.findMessagesBetweenMembers(senderMember.get().getId(), receiverMember.get().getId());
//        List<MessageReadDto> messageList = new ArrayList<>();
//
//        for (int i = 0; i < tempList.size(); i++) {
//            messageList.add(toReadDto(tempList.get(i)));
//        }
//
//        return messageList;

        return messageRepository.findMessagesBetweenMembers(senderMember.get().getId(), receiverMember.get().getId())
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private Message toEntity(MessageCreateDto createDto) {
        return Message.builder()
                .sender(memberRepository.findByNicknameAndIsDeleteFalse(createDto.getSenderNickname()).get())
                .receiver(memberRepository.findByNicknameAndIsDeleteFalse(createDto.getReceiverNickname()).get())
                .content(createDto.getContent())
                .build();
    }

    private MessageReadDto toReadDto(Message message) {
        return MessageReadDto.builder()
                .senderNickname(message.getSender().getNickname())
                .receiverNickname(message.getReceiver().getNickname())
                .content(message.getContent())
                .regDate(message.getRegDate())
                .build();
    }

    private MessageMemberDto toMsgMemberDto(Member member) {
        return MessageMemberDto.builder()
                .memberNickname(member.getNickname())
                .memberRole(String.valueOf(member.getRole()))
                .memberSkill(member.getSkill().getName())
                .build();
    }
}
