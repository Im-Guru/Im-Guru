package kr.co.imguru.domain.skill.service;

import kr.co.imguru.domain.skill.dto.SkillCreateDto;
import kr.co.imguru.domain.skill.dto.SkillReadDto;
import kr.co.imguru.domain.skill.dto.SkillUpdateDto;

import java.util.List;

public interface SkillService {

    Long createSkill(SkillCreateDto createDto);

    SkillReadDto getSkill(String skillName);

    List<SkillReadDto> getAllSkills();

    Long updateSkill(String skillName, SkillUpdateDto updateDto);

    void deleteSkill(String skillName);
}