package com.project.chamjimayo.repository;

import static com.project.chamjimayo.domain.entity.QUser.user;

import com.project.chamjimayo.service.dto.UserDetailsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public UserQueryRepository(EntityManager em) {
		this.jpaQueryFactory = new JPAQueryFactory(em);
	}

	public Optional<UserDetailsDto> findUserDetailsById(Long id) {
		return Optional.ofNullable(jpaQueryFactory.select(
				Projections.constructor(UserDetailsDto.class, user.name, user.nickname, user.point))
			.from(user)
			.where(user.userId.eq(id))
			.fetchOne());
	}
}
