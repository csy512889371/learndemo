package com.ctoedu.demo.core.position.repository;

import java.util.List;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.position.entity.UmsPosition;

/**
 *
 * Date:2016年11月23日 上午10:03:17
 * Version:1.0
 */
public interface UmsPositionRepository extends BaseRepository<UmsPosition,String> {
	UmsPosition findBySn(String sn);
	
	List<UmsPosition> findByDepartmentId(String departmentId);
	
	List<UmsPosition> findByOrgId(String orgId);
	
	void deleteByDepartmentId(String departmentId);
	
	void deleteByOrgId(String orgId);
}
