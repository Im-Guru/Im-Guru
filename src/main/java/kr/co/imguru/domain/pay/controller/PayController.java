//package kr.co.imguru.domain.pay.controller;
//
//
//import jakarta.servlet.http.HttpServletRequest;
//import kr.co.imguru.domain.pay.entity.Pay;
//import kr.co.imguru.domain.pay.repository.PayRepository;
//import kr.co.imguru.global.util.DateUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.List;
//
///**
// *
// * @author firstkhw
// * @date : 2019. 2. 25.
// * 통합인증 결제 관련 샘플 호출
// *
// */
//@Controller
//@Slf4j
//@RequiredArgsConstructor
//public class PayController {
//
//    private final PayRepository payRepo;
//
//    /**
//     * 기본 화면 로드
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index(Model model, HttpServletRequest request) throws Exception {
//        String mercntId = "ms00003t";
//        model.addAttribute("mercntId", mercntId);
//        model.addAttribute("ordNo", "SAMPLE_" + DateUtil.getDateTimeMillisecond());
//        model.addAttribute("baseUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
//
//        model.addAttribute("productNm", "테스트 상품");
//
//        return "../Users/soohykeee/Desktop/workspace/Im-Guru/front/src/views/pay/payIndex.html";
//    }
//
//    @RequestMapping(value = "/callback", method = RequestMethod.POST)
//    public String callback(Model model, HttpServletRequest request) throws Exception {
//        String resultCd = request.getParameter("resultCd");
//        String resultMsg = request.getParameter("resultMsg");
//        String mercntId = request.getParameter("mercntId");
//        String ordNo = request.getParameter("ordNo");
//        String authNo = request.getParameter("authNo");
//        String trPrice = request.getParameter("trPrice");
//        String discntPrice = request.getParameter("discntPrice");
//        String payPrice = request.getParameter("payPrice");
//        String trDay = request.getParameter("trDay");
//        String trTime = request.getParameter("trTime");
//        String mercntParam1 = request.getParameter("mercntParam1");
//        String mercntParam2 = request.getParameter("mercntParam2");
//
//        // PAY_RESERV 테이블 조회
//        Pay pay = payRepo.findByOrdNo(ordNo).orElseThrow(Exception::new);
//        String viewType = pay.getViewType();
//
//        if (resultCd.equals("0")) {
//            model.addAttribute("resultCd", resultCd);
//            model.addAttribute("resultMsg", resultMsg);
//            model.addAttribute("mercntId", mercntId);
//            model.addAttribute("authNo", authNo);
//            model.addAttribute("ordNo", ordNo);
//            model.addAttribute("trPrice", trPrice);
//            model.addAttribute("discntPrice", discntPrice);
//            model.addAttribute("payPrice", payPrice);
//            model.addAttribute("trDay", trDay);
//            model.addAttribute("trTime", trTime);
//            model.addAttribute("mercntParam1", mercntParam1);
//            model.addAttribute("mercntParam2", mercntParam2);
//            model.addAttribute("viewType", viewType);
//
//            pay.setAuthNo(authNo);
//            pay.setPayStatus("A");
//            payRepo.save(pay);
//        } else {
//            model.addAttribute("resultCd", resultCd);
//            model.addAttribute("ordNo", ordNo);
//            model.addAttribute("viewType", viewType);
//        }
//
//        return "callback";
//    }
//
//    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
//    public String cancel(Model model, HttpServletRequest request) throws Exception {
//        return "cancel";
//    }
//
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String list(Model model) throws Exception {
//        model.addAttribute("payList", payRepo.findAllByPayStatusIn(List.of("P", "C")));
//        return "list";
//    }
//}
//
