package com.project.chamjimayo.repository;

import static com.project.chamjimayo.domain.entity.QRestroom.restroom;
import static com.project.chamjimayo.domain.entity.QUsedRestroom.usedRestroom;
import static com.project.chamjimayo.domain.entity.QUser.user;

import com.project.chamjimayo.service.dto.RestroomSummaryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class RestroomQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public RestroomQueryRepository(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  public RestroomSummaryDto findUsingRestRoomDtoByUserId(Long id) {
    RestroomSummaryDto dto = jpaQueryFactory.select(
            Projections.constructor(RestroomSummaryDto.class,
                restroom.restroomId, restroom.restroomName, restroom.address,
                restroom.reviews.size(), restroom.operatingHour))
        .from(restroom)
        .innerJoin(user).on(restroom.restroomId.eq(user.usingRestroomId))
        .where(user.userId.eq(id).and(user.usingRestroomId.isNotNull()))
        .fetchOne();

    if (dto == null) {
      return RestroomSummaryDto.empty();
    }

    return dto;
  }

  public Page<RestroomSummaryDto> findUsedRestroomDtosByUserIdAndPageable
      (Long id, Pageable pageable) {
    List<RestroomSummaryDto> dtos = jpaQueryFactory.select(
            Projections.constructor(RestroomSummaryDto.class,
                restroom.restroomId, restroom.restroomName, restroom.address,
                restroom.reviews.size(), restroom.operatingHour))
        .from(restroom)
        .innerJoin(usedRestroom).on(restroom.restroomId.eq(usedRestroom.restroomId))
        .where(usedRestroom.user.userId.eq(id))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(usedRestroom.count())
        .from(usedRestroom)
        .where(usedRestroom.user.userId.eq(id))
        .fetchOne();

    return new PageImpl<>(dtos, pageable, count);
  }
}
