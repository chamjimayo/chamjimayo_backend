package com.project.chamjimayo.Repository.Entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "board_image")
@Getter
@ToString(exclude = "image_id")
@NoArgsConstructor
public class BoardImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Integer imageId;

	// 게시글 아이디 (어떤 게시글의 사진인가)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	// 사진 url
	@Column(name = "url")
	private String url;

	// 이미지 상태
	@Pattern(regexp = "[01]")
	@Column(name = "status")
	private boolean status;
}
