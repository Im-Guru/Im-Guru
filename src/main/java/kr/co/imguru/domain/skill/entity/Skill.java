package kr.co.imguru.domain.skill.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kr.co.imguru.domain.admin.dto.AdminSkillDto;
import kr.co.imguru.domain.skill.dto.SkillUpdateDto;
import kr.co.imguru.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Builder
    public Skill(String name) {
        this.name = name;
    }

    public void changeSkill(SkillUpdateDto updateDto) {
        this.name = updateDto.getName();
    }

    public void changeSkillByAdmin(AdminSkillDto updateDto) {
        this.name = updateDto.getName();
    }

}