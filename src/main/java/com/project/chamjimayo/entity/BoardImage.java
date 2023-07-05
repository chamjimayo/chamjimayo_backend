package com.project.chamjimayo.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "board_image")
public class BoardImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Integer imageId;

	// 게시글 아이디 (어떤 게시글의 사진인가)
	@Column(name = "board_id")
	private Integer boardId;

	// 사진 url
	@Column(name = "url")
	private String url;

	// 이미지 상태
	@Pattern(regexp = "[01]")
	@Column(name = "image_status")
	private boolean imageStatus;
}
