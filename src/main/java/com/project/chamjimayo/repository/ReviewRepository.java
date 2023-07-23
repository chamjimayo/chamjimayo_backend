package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.Restroom;
import com.project.chamjimayo.domain.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findAllByRestroom(Optional<Restroom> restroom);
}

