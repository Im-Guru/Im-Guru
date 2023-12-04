package kr.co.imguru.domain.pay.repository;

import kr.co.imguru.domain.pay.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long> {

    Optional<Pay> findByOrdNo(String ordNo);

    List<Pay> findAllByPayStatusIn(List<String> statusList);

}
