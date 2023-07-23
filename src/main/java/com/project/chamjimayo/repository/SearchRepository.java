package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.Search;
import com.project.chamjimayo.domain.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

	List<Search> findAllByUser(User user);

	void deleteAllByUser(User user);

	boolean existsByUserAndLatitudeAndLongitude(User user, double latitude, double longitude);
}




