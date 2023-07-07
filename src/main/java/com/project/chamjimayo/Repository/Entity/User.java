package com.project.chamjimayo.Repository.Entity;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@ToString(exclude = "user_id")
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	// 이름
	@Column(name = "name")
	private String name;

	// 이메일
	@Column(name = "email")
	private String email;

	// 포인트 (재화 <- 충전식)
	@Column(name = "point")
	private Integer point;

	// 성별
	@Column(name = "gender")
	private String gender;

	// 등록일
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	// 수정일
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	// 회원 상태
	@Pattern(regexp = "[01]")
	@Column(name = "status")
	private boolean status;

	// 프로필 사진 url
	@Column(name = "user_profile")
	private String userProfile;

	@OneToMany(mappedBy = "user")
	private List<Board> boards;

	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

	@OneToMany(mappedBy = "user")
	private List<Review> reviews;
}

