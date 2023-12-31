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
    public void createMessage(String email, MessageCreateDto createDto) {
        Optional<Member> sender = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(sender);

        Optional<Member> receiver = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getReceiverNickname());
        isMember(receiver);

        messageRepository.save(toEntity(createDto, sender.get(), receiver.get()));
    }

    //해당 회원과 메세지 받거나 보낸 회원 리스트
    @Override
    @Transactional
    public List<MessageMemberDto> getMessageByMember(String email) {
        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);
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
    @Transactional
    public List<MessageReadDto> getDetailMessage(String email, String member) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(loginMember);

        Optional<Member> messageMember = memberRepository.findByNicknameAndIsDeleteFalse(member);
        isMember(messageMember);

        return messageRepository.findMessagesBetweenMembers(loginMember.get().getId(), messageMember.get().getId())
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private Message toEntity(MessageCreateDto createDto, Member sender, Member receiver) {
        return Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(createDto.getContent())
                .build();
    }

    private MessageReadDto toReadDto(Message message) {
        return MessageReadDto.builder()
                .messageId(message.getId())
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
