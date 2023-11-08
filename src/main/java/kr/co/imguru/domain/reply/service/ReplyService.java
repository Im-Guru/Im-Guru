package kr.co.imguru.domain.reply.service;

import kr.co.imguru.domain.reply.dto.ReplyCreateDto;
import kr.co.imguru.domain.reply.dto.ReplyReadDto;
import kr.co.imguru.domain.reply.dto.ReplyUpdateDto;
import java.util.List;

public interface ReplyService {

    void createReply(ReplyCreateDto replyCreateDto);

    ReplyReadDto getReply(Long replyId);

    List<ReplyReadDto> getRepliesByPost(Long postId);

    List<ReplyReadDto> getRepliesByMember(String memberNickname);

    List<ReplyReadDto> getAllReplies();

    ReplyReadDto updateReply(Long replyId, ReplyUpdateDto updateDto);

    void deleteReply(Long replyId);
}
