package com.project.chamjimayo.Repository.Entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Getter
@ToString(exclude = "comment_id")
@NoArgsConstructor
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Integer commentId;

	// 게시글 아이디 (댓글이 달린 게시글)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	// 회원 아이디 (댓글을 쓴 회원)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 내용
	@Column(name = "content")
	private String content;

	// 추천 수
	@Column(name = "like_count")
	private Integer likeCount;

}

