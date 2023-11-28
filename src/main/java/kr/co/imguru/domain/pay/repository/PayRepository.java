package kr.co.imguru.domain.pay.repository;

import kr.co.imguru.domain.pay.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long> {

}
