package com.project.chamjimayo.domain.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "board")
@Getter
@ToString(exclude = "boardId")
@NoArgsConstructor
public class Board extends BaseEntity {

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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 조회수
	@Column(name = "view_count")
	private Integer viewCount;

	// 추천 수
	@Column(name = "like_count")
	private Integer likeCount;

	@OneToMany(mappedBy = "board")
	private List<Comment> comments;

	@OneToMany(mappedBy = "board")
	private List<BoardImage> boardImages;

}

