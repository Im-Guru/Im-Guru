package kr.co.imguru.domain.pay.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayReservDto {

    private String resCode;

    private String trPriceEnc;

    private String emailEnc;

    private String cphoneNoEnc;

    private String signature;

    private String trDay;

    private String trTime;

    private String viewType;

}
