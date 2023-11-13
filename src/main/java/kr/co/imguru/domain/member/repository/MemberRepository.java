package kr.co.imguru.domain.member.repository;

import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.global.common.Role;
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

    Optional<Member> findByEmailAndIsDeleteFalse(String email);

    List<Member> findAllByIsDeleteFalse();

    List<Member> findAllByRoleAndIsDeleteFalse(Role role);

    Optional<Member> findByIdAndIsDeleteFalse(Long id);
}
