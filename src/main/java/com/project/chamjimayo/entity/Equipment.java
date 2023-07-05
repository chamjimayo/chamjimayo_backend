package com.project.chamjimayo.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "equipment")
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "equipment_id")
	private Integer equipmentId;

	// 화장실 아이디 (해당 비품의 화장실)
	@Column(name = "restroom_id")
	private Integer restroomId;

	// 비품 이름
	@Column(name = "equipment_name")
	private String equipmentName;

	// 비품 사진 url
	@Column(name = "equipment_photo")
	private String equipmentPhoto;

	// 화장실 구역 (몇 번째 칸인가?)
	@Column(name = "restroom_section")
	private String restroomSection;
}

