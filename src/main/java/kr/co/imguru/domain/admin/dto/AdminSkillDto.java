package kr.co.imguru.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminSkillDto {

    Long skillId;

    String name;

    boolean isDelete;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
