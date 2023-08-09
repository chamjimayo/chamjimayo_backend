package com.project.chamjimayo.repository;

import com.project.chamjimayo.repository.domain.entity.RestroomPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestroomPhotoRepository extends JpaRepository<RestroomPhoto, Long> {

}
