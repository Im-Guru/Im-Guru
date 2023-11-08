package kr.co.imguru.domain.member.repository;

import kr.co.imguru.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmailAndIsDeleteFalse(String email);

    boolean existsByNicknameAndIsDeleteFalse(String nickname);

    boolean existsByTelephoneAndIsDeleteFalse(String telephone);

    Optional<Member> findByNicknameAndIsDeleteFalse(String nickname);

    List<Member> findAllByIsDeleteFalse();

    Optional<Member> findByIdAndIsDeleteFalse(Long id);
}
