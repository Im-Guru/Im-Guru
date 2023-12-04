package kr.co.imguru.domain.pay.entity;

import jakarta.persistence.*;
import kr.co.imguru.global.common.BaseEntity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;



    // 결제 인증
    @Column(name = "mercnt_id")
    private String mercntId;    // 가맹점 아이디

    @Column(name = "ord_no")
    private String ordNo;       // 주문번호

    @Column(name = "product_nm")
    private String productNm;   // 상품 명

    @Column(name = "tr_price")
    private Long trPrice;       // 거래 금액

    @Column(name = "tr_day")
    private String trDay;       // 거래 일자

    @Column(name = "tr_time")
    private String trTime;      // 거래 시간

    @Column(name = "signature")
    private String signature;   // hash 데이터 (암호화)

    @Column(name = "mercnt_param1")
    private String mercntParam1;    // 가맹점 측에서 원하는 경우 사용할 수 있는 필드 1

    @Column(name = "mercnt_param2")
    private String mercntParam2;    // 가맹점 측에서 원하는 경우 사용할 수 있는 필드 2

    @Column(name = "view_type")
    private String viewType;        // ???



    // 결제
    @Column(name = "auth_no")
    private String authNo;          // 결제 인증 번호

    @Column(name = "pay_result_cd")
    private String payResultCd;     // 결제 결과 코드

    @Column(name = "pay_result_msg")
    private String payResultMsg;    // 결제 결과 메세지

    @Column(name = "tr_no")
    private String trNo;            // 결제 거래 번호



    // 취소
    @Column(name = "cancel_ord_no")
    private String cancelOrdNo;     // 결제 취소 번호

    @Column(name = "cancel_result_cd")
    private String cancelResultCd;  // 결제 취소 결과 코드

    @Column(name = "cancel_result_msg")
    private String cancelResultMsg; // 결제 취소 결과 메세지

    @Column(name = "cancel_tr_no")
    private String cancelTrNo;      // 결제 취소 거래 번호




    @Column(name = "pay_status")
    private String payStatus; //    결제 상태 정보 -> O, A, P, C


    @Builder
    public Pay(String mercntId,
               String ordNo,
               String productNm,
               Long trPrice,
               String trDay,
               String trTime,
               String signature,
               String mercntParam1,
               String mercntParam2,
               String viewType,
               String authNo,
               String payResultCd,
               String payResultMsg,
               String trNo,
               String cancelOrdNo,
               String cancelResultCd,
               String cancelResultMsg,
               String cancelTrNo,
               String payStatus) {
        this.mercntId = mercntId;
        this.ordNo = ordNo;
        this.productNm = productNm;
        this.trPrice = trPrice;
        this.trDay = trDay;
        this.trTime = trTime;
        this.signature = signature;
        this.mercntParam1 = mercntParam1;
        this.mercntParam2 = mercntParam2;
        this.viewType = viewType;
        this.authNo = authNo;
        this.payResultCd = payResultCd;
        this.payResultMsg = payResultMsg;
        this.trNo = trNo;
        this.cancelOrdNo = cancelOrdNo;
        this.cancelResultCd = cancelResultCd;
        this.cancelResultMsg = cancelResultMsg;
        this.cancelTrNo = cancelTrNo;
        this.payStatus = payStatus;
    }


}
