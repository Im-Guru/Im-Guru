package kr.co.imguru.domain.skill.repository;

import kr.co.imguru.domain.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByNameAndIsDeleteFalse(String skillName);

    Optional<Skill> findByIdAndIsDeleteFalse(Long id);

    List<Skill> findAllByIsDeleteFalse();

    boolean existsByNameAndIsDeleteFalse(String skillName);
}
