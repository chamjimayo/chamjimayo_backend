package com.project.chamjimayo.dto;

public class SearchRequestDTO {

	private String searchWord;
	private Integer userId;

	public SearchRequestDTO(String searchWord, Integer userId) {
		this.searchWord = searchWord;
		this.userId = userId;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public Integer getUserId() {
		return userId;
	}
}
