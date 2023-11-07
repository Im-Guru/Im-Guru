package kr.co.imguru.domain.skill.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.skill.dto.SkillCreateDto;
import kr.co.imguru.domain.skill.dto.SkillReadDto;
import kr.co.imguru.domain.skill.dto.SkillUpdateDto;
import kr.co.imguru.domain.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SkillRestController {

    private final SkillService skillService;

    @PostMapping("/skill")
    public Long createSkill(@RequestBody @Valid SkillCreateDto createDto) {
        return skillService.createSkill(createDto);
    }

    @GetMapping("/skill/{skillName}")
    public SkillReadDto readSkill(@PathVariable String skillName) {
        return skillService.getSkill(skillName);
    }

    @GetMapping("/skill/all")
    public List<SkillReadDto> readAllSkills() {
        return skillService.getAllSkills();
    }

    @PutMapping("/skill/{skillName}")
    public Long updateSkill(@PathVariable String skillName,
                            @RequestBody @Valid SkillUpdateDto updateDto) {
        return skillService.updateSkill(skillName, updateDto);
    }

    @DeleteMapping("/skill/{skillName}")
    public void deleteSkill(@PathVariable String skillName) {
        skillService.deleteSkill(skillName);
    }
}
