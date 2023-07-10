package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsUserByNickname(String nickname);
  boolean existsUserByAuthId(String authId);
}
