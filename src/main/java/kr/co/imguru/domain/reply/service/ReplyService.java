package kr.co.imguru.domain.reply.service;

import kr.co.imguru.domain.reply.dto.ReplyCreateDto;
import kr.co.imguru.domain.reply.dto.ReplyReadDto;
import kr.co.imguru.domain.reply.dto.ReplyUpdateDto;

import java.util.List;

public interface ReplyService {

    Long createReply(String email, Long postId, ReplyCreateDto replyCreateDto);

    ReplyReadDto getReply(Long replyId);

    List<ReplyReadDto> getRepliesByPost(Long postId);

    List<ReplyReadDto> getRepliesByMember(String memberNickname);

    List<ReplyReadDto> getAllReplies();

    ReplyReadDto addLikeReply(String email, Long replyId);

    List<ReplyReadDto> getLikeRepliesByMember(String memberNickname);

    ReplyReadDto updateReply(Long replyId, ReplyUpdateDto updateDto);

    Long deleteReply(String email, Long postId, Long replyId);

    List<ReplyReadDto> getRepliesByLoginMember(String email);

    List<ReplyReadDto> getRepliesByMemberNickname(String memberNickname);
}
