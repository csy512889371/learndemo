package cn.ctoedu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.ctoedu.entity.SubwayStation;

/**
 *
 */
public interface SubwayStationRepository extends CrudRepository<SubwayStation, Long> {
    List<SubwayStation> findAllBySubwayId(Long subwayId);
}
