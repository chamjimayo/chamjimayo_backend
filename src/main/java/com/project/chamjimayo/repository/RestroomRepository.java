package com.project.chamjimayo.repository;


import com.project.chamjimayo.domain.entity.Restroom;
import com.project.chamjimayo.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestroomRepository extends JpaRepository<Restroom, Long> {

    @Query("SELECT DISTINCT r FROM Restroom r LEFT JOIN FETCH r.equipments WHERE r.publicOrPaid = :publicOrPaid")
    List<Restroom> findPublicOrPaid(@Param("publicOrPaid") String publicOrPaid);

}
