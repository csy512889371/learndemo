package cn.ctoedu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.ctoedu.entity.HouseTag;

/**
 *
 */
public interface HouseTagRepository extends CrudRepository<HouseTag, Long> {
    HouseTag findByNameAndHouseId(String name, Long houseId);

    List<HouseTag> findAllByHouseId(Long id);

    List<HouseTag> findAllByHouseIdIn(List<Long> houseIds);
}
