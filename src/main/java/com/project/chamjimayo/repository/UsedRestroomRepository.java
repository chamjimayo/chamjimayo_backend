package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.UsedRestroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedRestroomRepository extends JpaRepository<UsedRestroom, Long> {

  UsedRestroom findUsedRestroomByRestroomId(long restroomId);
}
