package kr.co.imguru.domain.skill.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.skill.dto.SkillCreateDto;
import kr.co.imguru.domain.skill.dto.SkillReadDto;
import kr.co.imguru.domain.skill.dto.SkillUpdateDto;
import kr.co.imguru.domain.skill.service.SkillService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SkillRestController {

    private final SkillService skillService;

    @PostMapping("/skill")
    public ResponseFormat<Void> createSkill(@RequestBody @Valid SkillCreateDto createDto) {
        skillService.createSkill(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @GetMapping("/skill/{skillName}")
    public ResponseFormat<SkillReadDto> readSkill(@PathVariable String skillName) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.getSkill(skillName));
    }

    @GetMapping("/skill/all")
    public ResponseFormat<List<SkillReadDto>> readAllSkills() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.getAllSkills());
    }

    @PutMapping("/skill/{skillName}")
    public ResponseFormat<SkillReadDto> updateSkill(@PathVariable String skillName,
                            @RequestBody @Valid SkillUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.updateSkill(skillName, updateDto));
    }

    @DeleteMapping("/skill/{skillName}")
    public ResponseFormat<Void> deleteSkill(@PathVariable String skillName) {
        skillService.deleteSkill(skillName);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }
}
