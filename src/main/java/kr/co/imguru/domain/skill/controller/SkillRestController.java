package kr.co.imguru.domain.skill.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.skill.dto.SkillCreateDto;
import kr.co.imguru.domain.skill.dto.SkillReadDto;
import kr.co.imguru.domain.skill.dto.SkillUpdateDto;
import kr.co.imguru.domain.skill.service.SkillService;
import kr.co.imguru.global.auth.CustomUserDetails;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/skill/{skillId}")
    public ResponseFormat<SkillReadDto> readSkill(@PathVariable Long skillId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.getSkill(skillId));
    }

    @GetMapping("/skill/all")
    public ResponseFormat<List<SkillReadDto>> readAllSkills() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.getAllSkills());
    }

    @PutMapping("/skill/{skillId}")
    public ResponseFormat<SkillReadDto> updateSkill(@PathVariable Long skillId,
                                                    @RequestBody @Valid SkillUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.updateSkill(skillId, updateDto));
    }

    @DeleteMapping("/skill/{skillId}")
    public ResponseFormat<Void> deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @PostMapping("/skill/checkGuruSkill")
    public ResponseFormat<SkillReadDto> checkGuruSkill(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, skillService.checkGuruSkill(userDetails.getUsername()));
    }
}
