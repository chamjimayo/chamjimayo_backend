package com.project.chamjimayo.repository;


import com.project.chamjimayo.domain.entity.Restroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestroomRepository extends JpaRepository<Restroom, Long> {

}
