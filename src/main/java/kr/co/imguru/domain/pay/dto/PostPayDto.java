package kr.co.imguru.domain.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostPayDto {

    // Post 관련
    private Long postId;

    private String authorNickname;

    private String postCategory;

    private String title;

    private String content;

    private boolean isGuru;

    private String skillName;

    private String price;

    private LocalDateTime regDate;


    // Pay 관련
    private String mercntId;

    private String ordNo;

    private String trDay;

    private String trTime;

    private Long trPrice;

    private String productNm;

    private String email;

    private String cphoneNo;

    private String mercntParam1;

    private String mercntParam2;

    private String signature;



    // Callback 관련
    private String authNo;

    private String resultCd;

    private String resultMsg;

    private String errCd;

    private String payPrice;

    private String discntPrice;

    private String viewType;

    private String payStatus;



}
