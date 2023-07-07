package com.project.chamjimayo.Repository.Entity;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "board")
@Getter
@ToString(exclude = "board_id")
@NoArgsConstructor
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
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

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

	@OneToMany(mappedBy = "board")
	private List<Comment> comments;

	@OneToMany(mappedBy = "board")
	private List<BoardImage> boardImages;
}

