package com.project.chamjimayo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserDetailsDto {

	private final String name;
	private final String nickname;
	private final Integer point;

	public UserDetailsDto(String name, String nickname, Integer point) {
		this.name = name;
		this.nickname = nickname;
		this.point = point;
	}

	@Schema(name = "UserDetailsDto.Response")
	@Getter
	public static class Response {

		private final String name;
		private final String nickname;
		private final Integer point;

		public Response(String name, String nickname, Integer point) {
			this.name = name;
			this.nickname = nickname;
			this.point = point;
		}
	}

	public Response toResponse() {
		return new Response(name, nickname, point);
	}
}
