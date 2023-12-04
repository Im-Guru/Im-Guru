package kr.co.imguru.domain.pay.controller;

import kr.co.imguru.domain.pay.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PayRestController {

    private final PayService payService;


}
