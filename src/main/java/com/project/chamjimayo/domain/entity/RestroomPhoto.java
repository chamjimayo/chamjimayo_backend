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
@Table(name = "restroom_photo")
@Getter
@ToString(exclude = "restroomPhotoId")
@NoArgsConstructor
public class RestroomPhoto extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restroom_photo_id")
  private Long restroomPhotoId;
  // 화장실 아이디 (어느 화장실을 관리하는지)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restroom_id")
  private Restroom restroom;

  @Column(name = "photo_url")
  private String photoUrl;

  public void createImage(Restroom restroom){
    this.restroom = restroom;
    this.photoUrl = "default 화장실 이미지 url";
  }
  public void createImage(Restroom restroom,String photoUrl){
    this.restroom = restroom;
    this.photoUrl = photoUrl;
  }
}
