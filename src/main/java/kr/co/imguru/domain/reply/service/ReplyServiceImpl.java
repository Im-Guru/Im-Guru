package kr.co.imguru.domain.reply.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.like.entity.LikeReply;
import kr.co.imguru.domain.like.repository.LikeReplyRepository;
import kr.co.imguru.domain.like.repository.LikeReplySearchRepository;
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
import kr.co.imguru.global.exception.DuplicatedException;
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

    private final LikeReplyRepository likeReplyRepository;

    private final ReplySearchRepository replySearchRepository;

    private final LikeReplySearchRepository likeReplySearchRepository;

    //Create
    @Override
    @Transactional
    public Long createReply(String email, Long postId, ReplyCreateDto createDto) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(loginMember);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);
        isPost(post);

        Reply reply = toEntity(createDto, loginMember.get(), post.get());

        replyRepository.save(reply);

        return reply.getId();
    }

    //Read One
    @Override
    @Transactional
    public ReplyReadDto getReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findByIdAndIsDeleteFalse(replyId);

        isReply(reply);

        return toReadDto(reply.get());
    }

    @Override
    @Transactional
    public List<ReplyReadDto> getRepliesByPost(Long postId) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);
        isPost(post);

        return replySearchRepository.findRepliesByPostId(postId)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
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
    @Transactional
    public List<ReplyReadDto> getAllReplies() {
        return replyRepository.findAllByIsDeleteFalse()
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public ReplyReadDto addLikeReply(String email, Long replyId) {
        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(member);

        Optional<Reply> reply = replyRepository.findByIdAndIsDeleteFalse(replyId);
        isReply(reply);

        isLikeReplyDuplicated(replyId, member.get().getId());

        LikeReply create = LikeReply.builder()
                .member(member.get())
                .reply(reply.get())
                .build();

        likeReplyRepository.save(create);

        reply.get().addLikeCnt();

        replyRepository.save(reply.get());

        return toReadDto(reply.get());
    }

    @Override
    @Transactional
    public List<ReplyReadDto> getLikeRepliesByMember(String memberNickname) {
        return replySearchRepository.findLikeRepliesByMemberNickname(memberNickname)
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
    public Long deleteReply(String email, Long postId, Long replyId) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(loginMember);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);
        isPost(post);

        Optional<Reply> reply = replyRepository.findByIdAndIsDeleteFalse(replyId);
        isReply(reply);

        isWriter(loginMember, reply);

        reply.get().changeDeleteAt();

        replyRepository.save(reply.get());

        return reply.get().getId();
    }

    @Override
    @Transactional
    public List<ReplyReadDto> getRepliesByLoginMember(String email) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);

        isMember(loginMember);

        return replySearchRepository.findRepliesByMemberNickname(loginMember.get().getNickname())
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ReplyReadDto> getRepliesByMemberNickname(String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        return replySearchRepository.findRepliesByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
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

    private void isLikeReplyDuplicated(Long replyId, Long memberId) {
        Optional<LikeReply> likeReply = Optional.ofNullable(likeReplySearchRepository.existsByReplyIdAndMemberId(replyId, memberId));

        if (likeReply.isPresent()) {
            throw new DuplicatedException(ResponseStatus.FAIL_REPLY_LIKE_DUPLICATED);
        }
    }

    private Reply toEntity(ReplyCreateDto dto, Member member, Post post) {
        return Reply.builder()
                .member(member)
                .post(post)
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
                .regDate(reply.getRegDate())
                .memberSkill(reply.getMember().getSkill().getName())
                .build();
    }

}
