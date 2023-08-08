package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.RestroomManager;
import com.project.chamjimayo.domain.entity.RestroomPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestroomPhotoRepository extends JpaRepository<RestroomPhoto, Long> {

}
