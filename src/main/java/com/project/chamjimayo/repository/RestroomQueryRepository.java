package com.project.chamjimayo.repository;

import static com.project.chamjimayo.repository.domain.entity.QRestroom.restroom;
import static com.project.chamjimayo.repository.domain.entity.QUsedRestroom.usedRestroom;
import static com.project.chamjimayo.repository.domain.entity.QUser.user;
import static com.project.chamjimayo.repository.domain.entity.QRestroomPhoto.restroomPhoto;

import com.project.chamjimayo.repository.domain.entity.Restroom;
import com.project.chamjimayo.service.dto.RestroomSummaryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
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
    Restroom entity = jpaQueryFactory
        .select(restroom)
        .from(restroom)
        .innerJoin(user).on(restroom.restroomId.eq(user.usingRestroomId))
        .leftJoin(restroom.restroomPhotos, restroomPhoto).fetchJoin()
        .where(user.userId.eq(id).and(user.usingRestroomId.isNotNull()))
        .fetchOne();

    if (entity == null) {
      return RestroomSummaryDto.empty();
    }

    return getRestroomSummaryDto(entity);
  }

  public Page<RestroomSummaryDto> findUsedRestroomDtosByUserIdAndPageable
      (Long id, Pageable pageable) {
    List<Restroom> entities = jpaQueryFactory
        .select(restroom)
        .from(restroom)
        .innerJoin(usedRestroom).on(restroom.restroomId.eq(usedRestroom.restroomId))
        .leftJoin(restroom.restroomPhotos, restroomPhoto).fetchJoin()
        .where(usedRestroom.user.userId.eq(id))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(usedRestroom.count())
        .from(usedRestroom)
        .where(usedRestroom.user.userId.eq(id))
        .fetchOne();

    List<RestroomSummaryDto> dtos = entities.stream()
        .map(this::getRestroomSummaryDto)
        .collect(Collectors.toList());

    return new PageImpl<>(dtos, pageable, count);
  }

  private RestroomSummaryDto getRestroomSummaryDto(Restroom r) {
    return RestroomSummaryDto.create(r.getRestroomId(), r.getRestroomName(), r.getAddress(),
        r.getReviews().size(), r.getOperatingHour(), r.getPrice(), r.getRestroomPhotos());
  }
}
