package com.project.chamjimayo.domain.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@ToString(exclude = "userId")
@NoArgsConstructor
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	// 이름
	@Column(name = "name")
	private String name;

	// 닉네임
	@Column(name = "nick_name")
	private String nickName;

	// 포인트 (재화 <- 충전식)
	@Column(name = "point")
	private Integer point;

	// 성별
	@Column(name = "gender")
	private String gender;

	// 프로필 사진 url
	@Column(name = "user_profile")
	private String userProfile;

	@Column(name = "role")
	private String role;

	@Column(name = "auth_id")
	private String authId;

	@Column(name = "auth_type")
	private String authType;

	@OneToMany(mappedBy = "user")
	private List<Board> boards;

	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

	@OneToMany(mappedBy = "user")
	private List<Review> reviews;

	@OneToMany(mappedBy = "user")
	private List<Search> searches;

}

