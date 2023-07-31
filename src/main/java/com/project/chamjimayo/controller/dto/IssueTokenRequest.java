package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.service.dto.IssueTokenDto;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueTokenRequest {

	@NotBlank
	@Schema(description = "사용자 식별 아이디")
	private String authId;

	@NotBlank
	@Schema(description = "refresh token")
	private String refreshToken;

	public IssueTokenDto toDto() {
		return IssueTokenDto.create(authId, refreshToken);
	}
}
