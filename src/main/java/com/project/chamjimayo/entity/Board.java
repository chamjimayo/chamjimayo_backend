package com.project.chamjimayo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Integer boardId;

	// 제목
	@Column(name = "title")
	private String title;

	// 내용
	@Column(name = "content")
	private String content;

	// 회원 아이디 (글을 쓴 회원)
	@Column(name = "user_id")
	private Integer userId;

	// 생성일
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	// 수정일
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	// 조회수
	@Column(name = "view_count")
	private Integer viewCount;

	// 추천 수
	@Column(name = "like_count")
	private Integer likeCount;

	// 게시글 상태
	@Pattern(regexp = "[01]")
	@Column(name = "board_status")
	private boolean boardStatus;
}

