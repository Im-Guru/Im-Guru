package kr.co.imguru.domain.pay.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.imguru.domain.pay.dto.PayApprovDto;
import kr.co.imguru.domain.pay.dto.PayCancelDto;
import kr.co.imguru.domain.pay.dto.PayCreateDto;
import kr.co.imguru.domain.pay.entity.Pay;
import kr.co.imguru.domain.pay.repository.PayRepository;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RestPayController {

    private final PayRepository payRepo;

//    @PostMapping("/payReserv")
//    public PayCreateDto payReserv(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        String encrypt_key = "SETTLEBANKISGOODSETTLEBANKISGOOD";
//
//        String hdInfo = request.getParameter("hdInfo");
//        String apiVer = request.getParameter("apiVer");
//        String processType = request.getParameter("processType");
//        String mercntId = request.getParameter("mercntId");
//        String ordNo = request.getParameter("ordNo");
//        String trPrice = request.getParameter("trPricePlain");
//        String productNm = request.getParameter("productNm");
//        String dutyFreeYn = request.getParameter("dutyFreeYn");
//        String addtionalDeductionType = request.getParameter("addtionalDeductionType");
//        String shopNm = request.getParameter("shopNm");
//        String cphoneNo = request.getParameter("cphoneNo");
//        String email = request.getParameter("email");
//        String callbackUrl = request.getParameter("callbackUrl");
//        String returnUrl = request.getParameter("returnUrl");
//        String cancelUrl = request.getParameter("cancelUrl");
//        String mercntParam1 = request.getParameter("mercntParam1");
//        String mercntParam2 = request.getParameter("mercntParam2");
//        String viewType = request.getParameter("viewType");
//        String trDay = DateUtil.currentDateString();
//        String trTime = DateUtil.currentTimeString();
//
//        String trPriceEnc = ChiperUtil.aesEncryptEcb(encrypt_key, trPrice);
//        String cphoneNoEnc = "";
//        if(cphoneNo != null && !cphoneNo.equals("")){
//            cphoneNoEnc = ChiperUtil.aesEncryptEcb(encrypt_key, cphoneNo);
//        }
//        String emailEnc = "";
//        if(email != null && !email.equals("")){
//            emailEnc = ChiperUtil.aesEncryptEcb(encrypt_key, email);
//        }
//
//        String hashValue = SignatureUtil.sha256(mercntId+ordNo+trDay+trTime+trPrice+encrypt_key);
//
//        //응답 파라메터
//        PayCreateDto payCreateDto = PayCreateDto.builder()
//                .resCode("0000")
//                .trPriceEnc(trPriceEnc)
//                .emailEnc(emailEnc)
//                .cphoneNoEnc(cphoneNoEnc)
//                .signature(hashValue)
//                .trDay(trDay)
//                .trTime(trTime)
//                .viewType(viewType)
//                .build();
//
//        // 주문 정보 저장
//        Pay order = Pay.builder()
//                .ordNo(ordNo)
//                .mercntId(mercntId)
//                .trPrice(Long.parseLong(trPrice))
//                .productNm(productNm)
//                .trDay(trDay)
//                .trTime(trTime)
//                .signature(hashValue)
//                .mercntParam1(mercntParam1)
//                .mercntParam2(mercntParam2)
//                .viewType(viewType)
//                .payStatus("O")
//                .build();
//        payRepo.save(order);
//
//        return payCreateDto;
//    }

    // TODO : 요청 시에 들어오는 모든 파라미터를 받는 DTO 생성 후, 거기에 해당하는 PayCreateDto, Pay 정보 생성 후 저장
    //
    @PostMapping("/payReserv")
    public PayCreateDto payReserv(HttpServletRequest request, @RequestBody PayCreateDto createDto) throws Exception {

        String encrypt_key = "SETTLEBANKISGOODSETTLEBANKISGOOD";

        String hdInfo = request.getParameter("hdInfo");
        String apiVer = request.getParameter("apiVer");
        String processType = request.getParameter("processType");
        String mercntId = request.getParameter("mercntId");
        String ordNo = request.getParameter("ordNo");
        String trPrice = request.getParameter("trPricePlain");
        String productNm = request.getParameter("productNm");
        String dutyFreeYn = request.getParameter("dutyFreeYn");
        String addtionalDeductionType = request.getParameter("addtionalDeductionType");
        String shopNm = request.getParameter("shopNm");
        String cphoneNo = request.getParameter("cphoneNo");
        String email = request.getParameter("email");
        String callbackUrl = request.getParameter("callbackUrl");
        String returnUrl = request.getParameter("returnUrl");
        String cancelUrl = request.getParameter("cancelUrl");
        String mercntParam1 = request.getParameter("mercntParam1");
        String mercntParam2 = request.getParameter("mercntParam2");
        String viewType = request.getParameter("viewType");
        String trDay = DateUtil.currentDateString();
        String trTime = DateUtil.currentTimeString();

        String trPriceEnc = ChiperUtil.aesEncryptEcb(encrypt_key, trPrice);
        String cphoneNoEnc = "";
        if(cphoneNo != null && !cphoneNo.equals("")){
            cphoneNoEnc = ChiperUtil.aesEncryptEcb(encrypt_key, cphoneNo);
        }
        String emailEnc = "";
        if(email != null && !email.equals("")){
            emailEnc = ChiperUtil.aesEncryptEcb(encrypt_key, email);
        }

        String hashValue = SignatureUtil.sha256(mercntId+ordNo+trDay+trTime+trPrice+encrypt_key);

        //응답 파라메터
        PayCreateDto payCreateDto = PayCreateDto.builder()
                .resCode("0000")
                .trPriceEnc(trPriceEnc)
                .emailEnc(emailEnc)
                .cphoneNoEnc(cphoneNoEnc)
                .signature(hashValue)
                .trDay(trDay)
                .trTime(trTime)
                .viewType(viewType)
                .build();

//        PayCreateDto payCreateDto = PayCreateDto.builder()
//                .resCode("0000")
//                .trPriceEnc(createDto.getTrPriceEnc())
//                .emailEnc(createDto.getEmailEnc())
//                .cphoneNoEnc(createDto.getCphoneNoEnc())
//                .signature(createDto.getSignature())
//                .trDay(createDto.getTrDay())
//                .trTime(createDto.getTrTime())
//                .viewType(createDto.getViewType())
//                .build();

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
        payRepo.save(order);

        return payCreateDto;
    }

    @RequestMapping(value = "/payAction", method = RequestMethod.POST)
    public PayApprovDto paymentAction(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String encrypt_key = "SETTLEBANKISGOODSETTLEBANKISGOOD";

        String hdInfo = request.getParameter("hdInfo");
        String apiVer = request.getParameter("apiVer");
        String mercntId = request.getParameter("mercntId");
        String authNo = request.getParameter("authNo");
        String reqDay = DateUtil.currentDateString();
        String reqTime = DateUtil.currentTimeString();
        String ordNo = request.getParameter("ordNo");

        String signature = SignatureUtil.sha256(mercntId+authNo+reqDay+reqTime+encrypt_key);

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

        Pay pay = payRepo.findByOrdNo(ordNo).orElseThrow(Exception::new);
        log.info(pay.toString());

        PayApprovDto payApprovDto = null;

        if ("0".equals(responseJSON.get("resultCd").toString())) {
            // 성공시 후처리
            pay.setPayResultCd("0");
            pay.setTrNo(responseJSON.get("trNo").toString());
            pay.setPayStatus("P");
            payRepo.save(pay);

            payApprovDto = PayApprovDto.builder()
                    .resultCd(responseJSON.get("resultCd").toString())
                    .trNo(responseJSON.get("trNo").toString())
                    .build();
        } else {
            // 실패시 후처리
            pay.setPayResultCd(responseJSON.get("errCd").toString());
            pay.setPayResultMsg(responseJSON.get("resultMsg").toString());
            payRepo.save(pay);

            payApprovDto = PayApprovDto.builder()
                    .resultCd(responseJSON.get("resultCd").toString())
                    .errCd(String.valueOf(responseJSON.get("errCd")))
                    .resultMsg(responseJSON.get("resultMsg").toString())
                    .build();
        }

        return payApprovDto;
    }

    @RequestMapping(value = "/payCancel", method = RequestMethod.POST)
    public PayCancelDto payCancel(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String encrypt_key = "SETTLEBANKISGOODSETTLEBANKISGOOD";

        String ordNo = request.getParameter("ordNo");
        Pay pay = payRepo.findByOrdNo(ordNo).orElseThrow(Exception::new);

        String hdInfo = "IA_CANCEL";
        String apiVer = "3.0";
        String mercntId = "ms00003t";
        String canelOrdNo = "CANCEL_" + request.getParameter("ordNo");
        String oldTrNo = request.getParameter("trNo");
        String cancelPrice = String.valueOf(pay.getTrPrice());
//		String taxPrice = request.getParameter("taxPrice");
//		String vatPrice = request.getParameter("vatPrice");
//		String dutyFreePrice = request.getParameter("dutyFreePrice");
        String cancelPriceEnc = ChiperUtil.aesEncryptEcb(encrypt_key, cancelPrice);
        String reqDay = DateUtil.currentDateString();
        String reqTime = DateUtil.currentTimeString();

        String signature = SignatureUtil.sha256(mercntId+oldTrNo+canelOrdNo+cancelPrice+reqDay+reqTime+encrypt_key);

        //요청 파라메터
        JSONObject reqParam = new JSONObject();
        reqParam.put("hdInfo", hdInfo);
        reqParam.put("apiVer", apiVer);
        reqParam.put("mercntId", mercntId);
        reqParam.put("oldTrNo", oldTrNo);
        reqParam.put("ordNo", canelOrdNo);
        reqParam.put("cancelPrice", cancelPriceEnc);
        reqParam.put("reqDay", reqDay);
        reqParam.put("reqTime", reqTime);
        reqParam.put("signature", signature);

        log.info("request json : " + reqParam.toString());

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

        PayCancelDto payCancelDto = null;

        if ("0".equals(responseJSON.get("resultCd").toString())) {
            pay.setCancelResultCd("0");
            pay.setCancelOrdNo(canelOrdNo);
            pay.setCancelTrNo(responseJSON.get("trNo").toString());
            pay.setPayStatus("C");
            payRepo.save(pay);

            payCancelDto = PayCancelDto.builder()
                    .resultCd(responseJSON.get("resultCd").toString())
                    .trNo(responseJSON.get("trNo").toString())
                    .build();
        } else {
            pay.setCancelResultCd(responseJSON.get("errCd").toString());
            pay.setCancelResultMsg(responseJSON.get("resultMsg").toString());
            pay.setCancelOrdNo(canelOrdNo);
            payRepo.save(pay);

            payCancelDto = PayCancelDto.builder()
                    .resultCd(responseJSON.get("resultCd").toString())
                    .errCd(String.valueOf(responseJSON.get("errCd")))
                    .resultMsg(responseJSON.get("resultMsg").toString())
                    .build();
        }

        return payCancelDto;
    }
}

