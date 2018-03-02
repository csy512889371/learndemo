package com.ctoedu.demo.core.department.repository;

import java.util.List;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;

/**
 *
 * Date:2016年11月23日 上午9:49:25
 * Version:1.0
 */
public interface UmsDepartmentRepository extends BaseRepository<UmsDepartment,String>{
	
	List<UmsDepartment> findByOrgId(String orgId);
}
