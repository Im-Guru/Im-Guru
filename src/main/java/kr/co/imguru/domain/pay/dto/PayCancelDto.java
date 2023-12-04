package kr.co.imguru.domain.pay.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayCancelDto {

    private String resultCd;

    private String errCd;

    private String resultMsg;

    private String trNo;

}
