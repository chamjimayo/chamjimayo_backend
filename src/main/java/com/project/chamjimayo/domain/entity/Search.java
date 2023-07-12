package com.project.chamjimayo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "search")
@Getter
@ToString(exclude = "searchId")
@NoArgsConstructor
public class Search {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "search_id")
	private Integer searchId;

	// 회원 아이디 (해당 검색 기록은 어떤 회원의 기록인가)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 검색어
	@Column(name = "search_word")
	private String searchWord;

	// 검색어에 대한 도로명 주소
	@Column(name = "road_address")
	private String roadAddress;

	// 검색어에 대한 지번 주소
	@Column(name = "lot_number_address")
	private String lotNumberAddress;

	// 검색에 대한 가게 이름
	@Column(name = "name")
	private String name;

	// 도로명 주소의 상태 (0: 클릭 전, 1: 클릭 후)
	@Column(name = "click")
	private int click;


	// 회원별 검색 기록을 위한 setter
	public Search(User user, String searchWord, String roadAddress, String lotNumberAddress, String name) {
		this.user = user;
		this.searchWord = searchWord;
		this.roadAddress = roadAddress;
		this.lotNumberAddress = lotNumberAddress;
		this.name = name;
		this.click = 0; // 초기 상태는 0으로 설정
	}

	// 클릭 처리를 하기 위한 setter
	public void setClick(int click) {
		this.click = click;
	}
}

