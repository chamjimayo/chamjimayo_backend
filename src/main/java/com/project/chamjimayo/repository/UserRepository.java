package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByAuthId(String authId);
  boolean existsUserByNickname(String nickname);
  boolean existsUserByAuthId(String authId);

}
