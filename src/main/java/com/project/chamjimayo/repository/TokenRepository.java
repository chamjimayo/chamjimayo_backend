package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
