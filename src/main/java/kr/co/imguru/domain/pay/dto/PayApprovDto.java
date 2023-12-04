package kr.co.imguru.domain.pay.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayApprovDto {

    private String resultCd;

    private String errCd;

    private String resultMsg;

    private String trNo;

}
