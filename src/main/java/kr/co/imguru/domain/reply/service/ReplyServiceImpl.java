package kr.co.imguru.domain.reply.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.post.repository.PostRepository;
import kr.co.imguru.domain.reply.dto.ReplyCreateDto;
import kr.co.imguru.domain.reply.dto.ReplyReadDto;
import kr.co.imguru.domain.reply.dto.ReplyUpdateDto;
import kr.co.imguru.domain.reply.entity.Reply;
import kr.co.imguru.domain.reply.repository.ReplyRepository;
import kr.co.imguru.domain.reply.repository.ReplySearchRepository;
import kr.co.imguru.global.exception.ForbiddenException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReplySearchRepository replySearchRepository;

    //Create
    @Override
    @Transactional
    public void createReply(ReplyCreateDto createDto) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getMemberNickname());
        isMember(member);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(createDto.getPostId());
        isPost(post);

        replyRepository.save(toEntity(createDto));
    }

    //Read One
    @Override
    public ReplyReadDto getReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findByIdAndIsDeleteFalse(replyId);

        isReply(reply);

        return toReadDto(reply.get());
    }

    @Override
    public List<ReplyReadDto> getRepliesByPost(Long postId) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);
        isPost(post);

        return replySearchRepository.findRepliesByPostId(postId)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    public List<ReplyReadDto> getRepliesByMember(String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);
        isMember(member);

        return replySearchRepository.findRepliesByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    //Read All
    @Override
    public List<ReplyReadDto> getAllReplies() {
        return replyRepository.findAllByIsDeleteFalse()
                .stream()
                .map(this::toReadDto)
                .toList();
    }


    //Update
    @Override
    @Transactional
    public ReplyReadDto updateReply(Long replyId, ReplyUpdateDto updateDto) {
        Optional<Reply> reply = replyRepository.findByIdAndIsDeleteFalse(replyId);
        isReply(reply);

        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(updateDto.getMemberNickname());
        isMember(member);

        isWriter(member, reply);

        reply.get().changeReply(updateDto);

        replyRepository.save(reply.get());

        return toReadDto(reply.get());
    }

    //Delete
    @Override
    @Transactional
    public void deleteReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findByIdAndIsDeleteFalse(replyId);
        isReply(reply);

        reply.get().changeDeleteAt();

        replyRepository.save(reply.get());
    }

    private void isMember(Optional<Member> member) {
        if(member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isReply(Optional<Reply> reply) {
        if(reply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPLY_NOT_FOUND);
        }
    }

    private void isWriter(Optional<Member> member, Optional<Reply> reply) {
        if(!member.get().getNickname().equals(reply.get().getMember().getNickname())) {
            throw new ForbiddenException(ResponseStatus.FAIL_REPLY_WRITER_NOT_MATCH);
        }
    }

    private Reply toEntity(ReplyCreateDto dto) {
        return Reply.builder()
                .member(memberRepository.findByNicknameAndIsDeleteFalse(dto.getMemberNickname()).get())
                .post(postRepository.findByIdAndIsDeleteFalse(dto.getPostId()).get())
                .content(dto.getContent())
                .likeCnt(0L)
                .build();
    }

    private ReplyReadDto toReadDto(Reply reply) {
        return ReplyReadDto.builder()
                .replyId(reply.getId())
                .memberNickname(reply.getMember().getNickname())
                .postId(reply.getPost().getId())
                .postTitle(reply.getPost().getTitle())
                .content(reply.getContent())
                .likeCnt(reply.getLikeCnt())
                .build();
    }

}
