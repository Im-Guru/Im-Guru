package kr.co.imguru.domain.message.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", referencedColumnName = "member_id")
    private Member sender;                      //보낸 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver", referencedColumnName = "member_id")
    private Member receiver;                    //받는 사람

    @Column(nullable = false, name="del_by_sender")
    private boolean delBySender = false;        //보낸 사람이 삭제

    @Column(nullable = false, name="del_by_receiver")
    private boolean delByReceiver = false;      //받는 사람이 삭제

    @Column(name = "content")
    @NotBlank
    private String content;

    @Builder
    public Message(Member sender,
                   Member receiver,
                   boolean delBySender,
                   boolean delByReceiver,
                   String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.delBySender = delBySender;
        this.delByReceiver = delByReceiver;
        this.content = content;
    }
}
