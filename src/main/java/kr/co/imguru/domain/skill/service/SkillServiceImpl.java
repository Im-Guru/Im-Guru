package kr.co.imguru.domain.skill.service;

import kr.co.imguru.domain.skill.dto.SkillCreateDto;
import kr.co.imguru.domain.skill.dto.SkillReadDto;
import kr.co.imguru.domain.skill.dto.SkillUpdateDto;
import kr.co.imguru.domain.skill.entity.Skill;
import kr.co.imguru.domain.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public Long createSkill(SkillCreateDto createDto) {
        isSkillName(createDto.getName());

        Skill skill = toEntity(createDto);

        skillRepository.save(skill);

        return skill.getId();
    }

    @Override
    public SkillReadDto getSkill(String skillName) {
        Optional<Skill> skill = skillRepository.findByNameAndIsDeleteFalse(skillName);

        isSkill(skill);

        return toReadDto(skill.get());
    }

    @Override
    public List<SkillReadDto> getAllSkills() {
        List<Skill> skillList = skillRepository.findAllByIsDeleteFalse();

        return skillList.stream().map(this::toReadDto).toList();
    }

    @Override
    public Long updateSkill(String skillName, SkillUpdateDto updateDto) {
        Optional<Skill> skill = skillRepository.findByNameAndIsDeleteFalse(skillName);

        isSkill(skill);

        skill.get().changeSkill(updateDto);

        skillRepository.save(skill.get());

        return skill.get().getId();
    }

    @Override
    public void deleteSkill(String skillName) {
        Optional<Skill> skill = skillRepository.findByNameAndIsDeleteFalse(skillName);

        isSkill(skill);

        skill.get().changeDeleteAt();

        skillRepository.save(skill.get());
    }

    private void isSkill(Optional<Skill> skill) {
        if (skill.isEmpty()) {
            throw new RuntimeException();
        }
    }

    private void isSkillName(String skillName) {
        if (skillRepository.existsByNameAndIsDeleteFalse(skillName)) {
            throw new RuntimeException();
        }
    }

    private Skill toEntity(SkillCreateDto dto) {
        return Skill.builder()
                .name(dto.getName())
                .build();
    }

    private SkillReadDto toReadDto(Skill skill) {
        return SkillReadDto.builder()
                .SkillId(skill.getId())
                .name(skill.getName())
                .build();
    }
}
