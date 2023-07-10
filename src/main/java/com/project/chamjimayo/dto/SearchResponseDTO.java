package com.project.chamjimayo.dto;

public class SearchResponseDTO {

	private String roadAddress;

	public SearchResponseDTO(String roadAddress) {
		this.roadAddress = roadAddress;
	}

	public String getRoadAddress() {
		return roadAddress;
	}
}

