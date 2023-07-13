package com.project.chamjimayo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "equipment")
@Getter
@ToString(exclude = "equipmentId")
@NoArgsConstructor
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "equipment_id")
	private Long equipmentId;

	// 화장실 아이디 (해당 비품의 화장실)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restroom_id")
	private Restroom restroom;

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

