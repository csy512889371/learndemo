package cn.ctoedu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.ctoedu.entity.Subway;

/**
 *
 */
public interface SubwayRepository extends CrudRepository<Subway, Long>{
    List<Subway> findAllByCityEnName(String cityEnName);
}
