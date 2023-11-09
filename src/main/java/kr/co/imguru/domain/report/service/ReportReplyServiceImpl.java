package kr.co.imguru.domain.report.service;

import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.reply.entity.Reply;
import kr.co.imguru.domain.reply.repository.ReplyRepository;
import kr.co.imguru.domain.report.dto.ReportReplyCreateDto;
import kr.co.imguru.domain.report.dto.ReportReplyReadDto;
import kr.co.imguru.domain.report.entity.ReportReply;
import kr.co.imguru.domain.report.repository.ReportReplyRepository;
import kr.co.imguru.global.common.ReportCategory;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.IllegalArgumentException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportReplyServiceImpl implements ReportReplyService {

    private final ReportReplyRepository reportReplyRepository;

    private final MemberRepository memberRepository;

    private final ReplyRepository replyRepository;

    @Override
    public void createReportReply(ReportReplyCreateDto createDto) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getMemberNickname());
        isMember(member);

        Optional<Reply> reply = replyRepository.findByIdAndIsDeleteFalse(createDto.getReplyId());
        isReply(reply);

        isReportCategory(createDto.getCategoryName());

        isReportReplyDuplicated(createDto.getReplyId(), createDto.getMemberNickname());

        reportReplyRepository.save(toEntity(createDto));
    }

    @Override
    public ReportReplyReadDto getReportReply(Long reportReplyId) {
        Optional<ReportReply> reportReply = reportReplyRepository.findById(reportReplyId);

        isReportReply(reportReply);

        return toReadDto(reportReply.get());
    }

    @Override
    public List<ReportReplyReadDto> getReportRepliesByReply(Long replyId) {
        return reportReplyRepository.findAllByReply_Id(replyId)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    public List<ReportReplyReadDto> getReportRepliesByMember(String memberNickname) {
        return reportReplyRepository.findAllByMember_Nickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    public List<ReportReplyReadDto> getAllReportReplies() {
        return reportReplyRepository.findAll()
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isReply(Optional<Reply> reply) {
        if (reply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPLY_NOT_FOUND);
        }
    }

    private void isReportCategory(String categoryName) {
        try {
            ReportCategory.valueOf(categoryName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ResponseStatus.FAIL_REPORT_CATEGORY_NOT_FOUND);
        }
    }

    private void isReportReply(Optional<ReportReply> reportReply) {
        if (reportReply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPORT_REPLY_NOT_FOUND);
        }
    }

    private void isReportReplyDuplicated(Long replyId, String memberNickname) {
        if (reportReplyRepository.existsByReply_IdAndMember_NicknameAndIsDeleteFalse(replyId, memberNickname)) {
            throw new DuplicatedException(ResponseStatus.FAIL_REPORT_DUPLICATED);
        }
    }

    private ReportReply toEntity(ReportReplyCreateDto dto) {
        return ReportReply.builder()
                .member(memberRepository.findByNicknameAndIsDeleteFalse(dto.getMemberNickname()).get())
                .reply(replyRepository.findByIdAndIsDeleteFalse(dto.getReplyId()).get())
                .reportCategory(ReportCategory.valueOf(dto.getCategoryName()))
                .description(dto.getDescription())
                .isAccept(false)
                .build();
    }

    private ReportReplyReadDto toReadDto(ReportReply reportReply) {
        return ReportReplyReadDto.builder()
                .reportReplyId(reportReply.getId())
                .memberNickname(reportReply.getMember().getNickname())
                .replyId(reportReply.getReply().getId())
                .replyContent(reportReply.getReply().getContent())
                .replyWriter(reportReply.getReply().getMember().getNickname())
                .categoryName(String.valueOf(reportReply.getReportCategory()))
                .description(reportReply.getDescription())
                .build();
    }
}
