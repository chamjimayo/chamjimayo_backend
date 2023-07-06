package com.project.chamjimayo.Repository.Entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Getter
@ToString(exclude = "comment_id")
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Integer commentId;

	// 게시글 아이디 (댓글이 달린 게시글)
	@Column(name = "board_id")
	private Integer boardId;

	// 회원 아이디 (댓글을 쓴 회원)
	@Column(name = "user_id")
	private Integer userId;

	// 내용
	@Column(name = "content")
	private String content;

	// 생성일
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	// 수정일
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	// 추천 수
	@Column(name = "like_count")
	private Integer likeCount;

	// 댓글 상태
	@Pattern(regexp = "[01]")
	@Column(name = "comment_status")
	private boolean commentStatus;
}

