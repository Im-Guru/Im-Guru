package kr.co.imguru.domain.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayCreateDto {

//    private String hdiInfo;

//    private String apiVer;

//    private String processType;

//    private String dutyFreeYn;

//    private String trTime;

//    private String trDay;

    private String mercntId;

    private String ordNo;

    private Long trPrice;

    private String trPricePlain;

    private String productNm;

    private String mercntParam1;

    private String mercntParam2;

    private String viewType;

    private String email;

    private String cphoneNo;

    private String signature;


}
