package kr.co.imguru.domain.pay.service;

import kr.co.imguru.domain.pay.dto.PostPayDto;
import kr.co.imguru.domain.pay.entity.Pay;

import java.util.List;

public interface PayService {

    PostPayDto createPayByPost(String email, Long postId);

    List<Pay> getAllSuccessPays();

    List<Pay> getPaysByLoginMember(String email);

}
