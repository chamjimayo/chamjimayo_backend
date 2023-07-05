package com.project.chamjimayo.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "restroom_manager")
public class RestroomManager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manager_id")
	private Integer managerId;

	// 화장실 아이디 (어느 화장실을 관리하는지)
	@Column(name = "restroom_id")
	private Integer restroomId;

	// 전화번호
	@Column(name = "phone_number")
	private String phoneNumber;

	// 이름
	@Column(name = "name")
	private String name;

	// 이메일
	@Column(name = "email")
	private String email;

	// 등록일
	@Column(name = "registration_date")
	private LocalDateTime registrationDate;

	// 수정일
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	// 관리자 상태
	@Pattern(regexp = "[01]")
	@Column(name = "manager_status")
	private boolean managerStatus;
}
