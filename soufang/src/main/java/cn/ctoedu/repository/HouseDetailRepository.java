package cn.ctoedu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.ctoedu.entity.HouseDetail;

/**
 *
 */
public interface HouseDetailRepository extends CrudRepository<HouseDetail, Long>{
    HouseDetail findByHouseId(Long houseId);

    List<HouseDetail> findAllByHouseIdIn(List<Long> houseIds);
}
