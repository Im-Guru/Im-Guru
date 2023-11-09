package kr.co.imguru.domain.guru.repository;

import kr.co.imguru.domain.guru.entity.GuruInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuruInfoRepository extends JpaRepository<GuruInfo, Long> {

    List<GuruInfo> findAllByIsDeleteFalse();

}
