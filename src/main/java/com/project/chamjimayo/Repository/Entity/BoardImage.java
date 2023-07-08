package com.project.chamjimayo.Repository.Entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "board_image")
@Getter
@ToString(exclude = "image_id")
@NoArgsConstructor
public class BoardImage extends BaseEntity {

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

}
