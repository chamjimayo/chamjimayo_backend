package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.RestroomManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestroomManagerRepository extends JpaRepository<RestroomManager, Long> {

}
