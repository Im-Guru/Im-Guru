package kr.co.imguru.domain.skill.service;

import kr.co.imguru.domain.skill.dto.SkillCreateDto;
import kr.co.imguru.domain.skill.dto.SkillReadDto;
import kr.co.imguru.domain.skill.dto.SkillUpdateDto;

import java.util.List;

public interface SkillService {

    void createSkill(SkillCreateDto createDto);

    SkillReadDto getSkill(Long skillId);

    List<SkillReadDto> getAllSkills();

    SkillReadDto updateSkill(Long skillId, SkillUpdateDto updateDto);

    void deleteSkill(Long skillId);

    SkillReadDto checkGuruSkill(String email);

}
