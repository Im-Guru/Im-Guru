package kr.co.imguru.domain.pay.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.pay.entity.Pay;
import kr.co.imguru.domain.pay.repository.PayRepository;
import kr.co.imguru.domain.pay.dto.PostPayDto;
import kr.co.imguru.domain.pay.repository.PaySearchRepository;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.post.repository.PostRepository;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import kr.co.imguru.global.util.DateUtil;
import kr.co.imguru.global.util.SignatureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final PaySearchRepository paySearchRepository;

    @Override
    @Transactional
    public PostPayDto createPayByPost(String email, Long postId) {

        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(member);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);
        isPost(post);

        String encrypt_key = "SETTLEBANKISGOODSETTLEBANKISGOOD";

        String trPrice = post.get().getPrice();
        String trDay = DateUtil.currentDateString();
        String trTime = DateUtil.currentTimeString();
        String ordNo = "M2266041-" + trDay + trTime + "-" + postId + member.get().getId();
        String productNm = post.get().getMember().getSkill().getName() + "-" + post.get().getMember().getNickname() + "-" + post.get().getTitle();
        String hashValue = SignatureUtil.sha256("M2266041" + ordNo + trDay + trTime + trPrice + encrypt_key);

        return PostPayDto.builder()
                .postId(postId)
                .authorNickname(post.get().getMember().getNickname())
                .postCategory(String.valueOf(post.get().getPostCategory()))
                .title(post.get().getTitle())
                .content(post.get().getContent())
                .isGuru(post.get().isGuru())
                .skillName(post.get().getMember().getSkill().getName())
                .price(post.get().getPrice())
                .regDate(post.get().getRegDate())
                .mercntId("M2266041")
                .ordNo(ordNo)
                .trDay(trDay)
                .trTime(trTime)
                .trPrice(Long.valueOf(trPrice))
                .productNm(productNm)
                .email(email)
                .cphoneNo(member.get().getTelephone())
                .mercntParam1(String.valueOf(postId))
                .mercntParam2(String.valueOf(member.get().getId()))
                .signature(hashValue)
                .build();

    }

    @Override
    @Transactional
    public List<Pay> getAllSuccessPays() {
        return payRepository.findAllByPayStatusIn(List.of("P", "C"));
    }

    @Override
    @Transactional
    public List<Pay> getPaysByLoginMember(String email) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(loginMember);

        return paySearchRepository.findPaysByMemberId(loginMember.get().getId());

    }


    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }



}
