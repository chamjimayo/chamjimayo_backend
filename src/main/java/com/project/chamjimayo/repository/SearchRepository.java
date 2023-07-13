package com.project.chamjimayo.repository;

import com.project.chamjimayo.domain.entity.Search;
import com.project.chamjimayo.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer> {

	Optional<Search> findTopByUserAndClickOrderBySearchIdDesc(User user, int i);

}




