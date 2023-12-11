package kr.co.imguru.domain.pay.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.imguru.domain.pay.dto.PayApprovDto;
import kr.co.imguru.domain.pay.dto.PayCancelDto;
import kr.co.imguru.domain.pay.dto.PayCreateDto;
import kr.co.imguru.domain.pay.dto.PayReservDto;
import kr.co.imguru.domain.pay.entity.Pay;
import kr.co.imguru.domain.pay.repository.PayRepository;
import kr.co.imguru.domain.pay.service.PayService;
import kr.co.imguru.domain.pay.dto.PostPayDto;
import kr.co.imguru.domain.post.dto.PostReadDto;
import kr.co.imguru.global.auth.CustomUserDetails;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import kr.co.imguru.global.util.ChiperUtil;
import kr.co.imguru.global.util.DateUtil;
import kr.co.imguru.global.util.SignatureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PayRestController {

    private final PayRepository payRepository;

    private final PayService payService;

    @PostMapping("/pay/post/{postId}")
    public ResponseFormat<PostPayDto> createPayByPost(@AuthenticationPrincipal UserDetails userDetails,
                                                      @PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, payService.createPayByPost(userDetails.getUsername(), postId));
    }

    @PostMapping("/pay/list/all")
    public ResponseFormat<List<Pay>> getAllSuccessPays() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, payService.getAllSuccessPays());
    }

    @PostMapping("/pay/myPay")
    public ResponseFormat<List<Pay>> getPaysByLoginMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, payService.getPaysByLoginMember(userDetails.getUsername()));
    }

    @PostMapping("/payReserv")
    public PayReservDto payReserv(@RequestBody PayCreateDto createDto) throws Exception {

        String encrypt_key = "SETTLEBANKISGOODSETTLEBANKISGOOD";

        String mercntId = createDto.getMercntId();
        String ordNo = createDto.getOrdNo();
//        String trPrice = String.valueOf(createDto.getTrPrice());
        String trPrice = createDto.getTrPricePlain();
        String productNm = createDto.getProductNm();
        String cphoneNo = createDto.getCphoneNo();
        String email = createDto.getEmail();
        String mercntParam1 = createDto.getMercntParam1();
        String mercntParam2 = createDto.getMercntParam2();
        String viewType = createDto.getViewType();
        String trDay = DateUtil.currentDateString();
        String trTime = DateUtil.currentTimeString();

        String trPriceEnc = ChiperUtil.aesEncryptEcb(encrypt_key, trPrice);
        String cphoneNoEnc = "";
        if (cphoneNo != null && !cphoneNo.equals("")) {
            cphoneNoEnc = ChiperUtil.aesEncryptEcb(encrypt_key, cphoneNo);
        }
        String emailEnc = "";
        if (email != null && !email.equals("")) {
            emailEnc = ChiperUtil.aesEncryptEcb(encrypt_key, email);
        }

        String hashValue = SignatureUtil.sha256(mercntId + ordNo + trDay + trTime + trPrice + encrypt_key);

        //응답 파라메터
        PayReservDto payReservDto = PayReservDto.builder()
                .resCode("0000")
                .trPriceEnc(trPriceEnc)
                .emailEnc(emailEnc)
                .cphoneNoEnc(cphoneNoEnc)
                .signature(hashValue)
                .trDay(trDay)
                .trTime(trTime)
                .viewType(viewType)
                .build();

        // 주문 정보 저장
        Pay order = Pay.builder()
                .ordNo(ordNo)
                .mercntId(mercntId)
                .trPrice(Long.parseLong(trPrice))
                .productNm(productNm)
                .trDay(trDay)
                .trTime(trTime)
                .signature(hashValue)
                .mercntParam1(mercntParam1)
                .mercntParam2(mercntParam2)
                .viewType(viewType)
                .payStatus("O")
                .build();

        payRepository.save(order);

        return payReservDto;
    }

    @PostMapping("/pay/callback")
    public RedirectView callback(HttpServletRequest request, RedirectAttributes attributes) throws Exception {
        String resultCd = request.getParameter("resultCd");
        String resultMsg = request.getParameter("resultMsg");
        String mercntId = request.getParameter("mercntId");
        String ordNo = request.getParameter("ordNo");
        String authNo = request.getParameter("authNo");
        String trPrice = request.getParameter("trPrice");
        String discntPrice = request.getParameter("discntPrice");
        String payPrice = request.getParameter("payPrice");
        String trDay = request.getParameter("trDay");
        String trTime = request.getParameter("trTime");
        String mercntParam1 = request.getParameter("mercntParam1");
        String mercntParam2 = request.getParameter("mercntParam2");

        // PAY_RESERV 테이블 조회
        Pay pay = payRepository.findByOrdNo(ordNo).orElseThrow(Exception::new);
        String viewType = pay.getViewType();

        if (resultCd.equals("0")) {
            pay.setAuthNo(request.getParameter("authNo"));
            pay.setPayStatus("A");
            payRepository.save(pay);

            PostPayDto postPayDto = PostPayDto.builder()
                    .resultCd(resultCd)
                    .resultMsg(resultMsg)
                    .mercntId(mercntId)
                    .authNo(authNo)
                    .ordNo(ordNo)
                    .trPrice(Long.valueOf(trPrice))
                    .discntPrice(discntPrice)
                    .payPrice(payPrice)
                    .trDay(trDay)
                    .trTime(trTime)
                    .mercntParam1(mercntParam1)
                    .mercntParam2(mercntParam2)
                    .viewType(viewType)
                    .build();

            attributes.addFlashAttribute("postPayDto", postPayDto);

            String frontendRedirectUrl = "http://localhost:3000/pay/callback?resultCd=" + resultCd +
                    "&resultMsg=" + resultMsg +
                    "&mercntId=" + mercntId +
                    "&ordNo=" + ordNo +
                    "&authNo=" + authNo +
                    "&trPrice=" + trPrice +
                    "&discntPrice=" + discntPrice +
                    "&payPrice=" + payPrice +
                    "&trDay=" + trDay +
                    "&trTime=" + trTime +
                    "&mercntParam1=" + mercntParam1 +
                    "&mercntParam2=" + mercntParam2 +
                    "&viewType=" + viewType;

            return new RedirectView(frontendRedirectUrl, true, false);
        } else {
            PostPayDto postPayDto = PostPayDto.builder()
                    .resultCd(resultCd)
                    .ordNo(ordNo)
                    .viewType(viewType)
                    .build();

            attributes.addFlashAttribute("postPayDto", postPayDto);

            String frontendRedirectUrl = "http://localhost:3000/pay/callback?resultCd=" + resultCd +
                    "&ordNo=" + ordNo +
                    "&viewType=" + viewType;

            return new RedirectView(frontendRedirectUrl, true, false);
        }
    }

    @PostMapping("/payAction")
    public PayApprovDto paymentAction(@RequestBody PostPayDto postPayDto) throws Exception {

        String encrypt_key = "SETTLEBANKISGOODSETTLEBANKISGOOD";

        String hdInfo = "IA_APPROV";
        String apiVer = "3.0";
        String mercntId = postPayDto.getMercntId();
        String authNo = postPayDto.getAuthNo();
        String reqDay = DateUtil.currentDateString();
        String reqTime = DateUtil.currentTimeString();
        String ordNo = postPayDto.getOrdNo();

        String signature = SignatureUtil.sha256(mercntId + authNo + reqDay + reqTime + encrypt_key);

        //요청 파라메터
        JSONObject reqParam = new JSONObject();
        reqParam.put("hdInfo", hdInfo);
        reqParam.put("apiVer", apiVer);
        reqParam.put("mercntId", mercntId);
        reqParam.put("authNo", authNo);
        reqParam.put("reqDay", reqDay);
        reqParam.put("reqTime", reqTime);
        reqParam.put("signature", signature);

        log.info("request json : " + reqParam.toString());
        System.out.println("----payAction Request Log-----");
        System.out.println(reqParam.toString());

        // 응답 파라메터
        JSONObject responseJSON = null;

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        ResponseEntity<String> payApprovResponse = rt.exchange(
                "https://tbezauthapi.settlebank.co.kr/APIPayApprov.do",
                HttpMethod.POST,
                new HttpEntity<>(reqParam.toString(), headers),
                String.class);
        responseJSON = new JSONObject(payApprovResponse.getBody());

        log.info(responseJSON.toString());
        System.out.println("----payAction Response Log-----");
        System.out.println(responseJSON.toString());

        Pay pay = payRepository.findByOrdNo(ordNo).orElseThrow(Exception::new);
        log.info(pay.toString());

        PayApprovDto payApprovDto = null;

        if ("0".equals(responseJSON.get("resultCd").toString())) {
            // 성공시 후처리
            pay.setPayResultCd("0");
            pay.setTrNo(responseJSON.get("trNo").toString());
            pay.setPayStatus("P");
            payRepository.save(pay);

            payApprovDto = PayApprovDto.builder()
                    .resultCd(responseJSON.get("resultCd").toString())
                    .trNo(responseJSON.get("trNo").toString())
                    .build();
        } else {
            // 실패시 후처리
            pay.setPayResultCd(responseJSON.get("errCd").toString());
            pay.setPayResultMsg(responseJSON.get("resultMsg").toString());
            payRepository.save(pay);

            payApprovDto = PayApprovDto.builder()
                    .resultCd(responseJSON.get("resultCd").toString())
                    .errCd(String.valueOf(responseJSON.get("errCd")))
                    .resultMsg(responseJSON.get("resultMsg").toString())
                    .build();
        }

        return payApprovDto;
    }

    @PostMapping("/payCancel/{ordNo}/{trNo}")
    public PayCancelDto payCancel(@PathVariable String ordNo,
                                  @PathVariable String trNo) throws Exception {

        String encrypt_key = "SETTLEBANKISGOODSETTLEBANKISGOOD";

        Pay pay = payRepository.findByOrdNo(ordNo).orElseThrow(Exception::new);

        String hdInfo = "IA_CANCEL";
        String apiVer = "3.0";
        String mercntId = "M2266041";
        String cancelOrdNo = "CANCEL_" + ordNo;
        String oldTrNo = trNo;
        String cancelPrice = String.valueOf(pay.getTrPrice());
        String cancelPriceEnc = ChiperUtil.aesEncryptEcb(encrypt_key, cancelPrice);
        String reqDay = DateUtil.currentDateString();
        String reqTime = DateUtil.currentTimeString();

        String signature = SignatureUtil.sha256(mercntId + oldTrNo + cancelOrdNo + cancelPrice + reqDay + reqTime + encrypt_key);

        //요청 파라메터
        JSONObject reqParam = new JSONObject();
        reqParam.put("hdInfo", hdInfo);
        reqParam.put("apiVer", apiVer);
        reqParam.put("mercntId", mercntId);
        reqParam.put("oldTrNo", oldTrNo);
        reqParam.put("ordNo", cancelOrdNo);
        reqParam.put("cancelPrice", cancelPriceEnc);
        reqParam.put("reqDay", reqDay);
        reqParam.put("reqTime", reqTime);
        reqParam.put("signature", signature);

        log.info("request json : " + reqParam.toString());
        System.out.println("----payCancel Request Log-----");
        System.out.println(reqParam.toString());

        // 응답 파라메터
        JSONObject responseJSON = null;

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        ResponseEntity<String> payCancelResponse = rt.exchange(
                "https://tbezauthapi.settlebank.co.kr/APIPayCancel.do",
                HttpMethod.POST,
                new HttpEntity<>(reqParam.toString(), headers),
                String.class);
        responseJSON = new JSONObject(payCancelResponse.getBody());

        log.info(responseJSON.toString());

        System.out.println("----payCancel Response Log-----");
        System.out.println(responseJSON.toString());

        PayCancelDto payCancelDto = null;

        if ("0".equals(responseJSON.get("resultCd").toString())) {
            pay.setCancelResultCd("0");
            pay.setCancelOrdNo(cancelOrdNo);
            pay.setCancelTrNo(responseJSON.get("trNo").toString());
            pay.setPayStatus("C");
            payRepository.save(pay);

            payCancelDto = PayCancelDto.builder()
                    .resultCd(responseJSON.get("resultCd").toString())
                    .trNo(responseJSON.get("trNo").toString())
                    .build();
        } else {
            pay.setCancelResultCd(responseJSON.get("errCd").toString());
            pay.setCancelResultMsg(responseJSON.get("resultMsg").toString());
            pay.setCancelOrdNo(cancelOrdNo);
            payRepository.save(pay);

            payCancelDto = PayCancelDto.builder()
                    .resultCd(responseJSON.get("resultCd").toString())
                    .errCd(String.valueOf(responseJSON.get("errCd")))
                    .resultMsg(responseJSON.get("resultMsg").toString())
                    .build();
        }

        return payCancelDto;
    }
}

