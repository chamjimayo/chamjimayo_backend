package com.project.chamjimayo.repository.domain.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
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
  private Long userId;

  // 이름
  @Column(name = "name")
  private String name;

  // 닉네임
  @Column(name = "nickname")
  private String nickname;

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
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "auth_id")
  private String authId;

  @Column(name = "auth_type")
  @Enumerated(EnumType.STRING)
  private AuthType authType;

  @OneToMany(mappedBy = "user")
  private List<Board> boards;

  @OneToMany(mappedBy = "user")
  private List<Comment> comments;

  @OneToMany(mappedBy = "user")
  private List<Review> reviews;

  @OneToMany(mappedBy = "user")
  private List<Search> searches;

  @OneToMany(mappedBy = "user")
  private List<UsedRestroom> usedRestrooms;

  @Column(name = "using_restroom_id")
  private Long usingRestroomId;

  private User(String name, String nickname, Integer point, String gender,
      String userProfile, Role role, String authId, AuthType authType) {
    this.name = name;
    this.nickname = nickname;
    this.point = point;
    this.gender = gender;
    this.userProfile = userProfile;
    this.role = role;
    this.authId = authId;
    this.authType = authType;
  }

  public static User createNewUser(String name, String nickname, String gender,
      String userProfile, String authId, AuthType authType) {
    return new User(name, nickname, 0, gender, userProfile, Role.ROLE_USER, authId, authType);
  }

  // 포인트 충전을 위한 로직
  public void addPoint(Integer newPoint) {
    this.point = this.point + newPoint;
  }

  // 포인트 차감을 위한 로직
  public void deductPoint(Integer deductionPoint) {
    this.point = this.point - deductionPoint;
  }

  public void useRestroom(long restroomId) {
    this.usingRestroomId = restroomId;
  }

  public void endOfUseRestroom() {
    this.usingRestroomId = null;
  }

  public void changeNickname(String nickname) {
    this.nickname = nickname;
  }

  public void changeUserProfile(String userProfile) {
    this.userProfile = userProfile;
  }
}
