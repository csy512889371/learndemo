package com.ctoedu.demo.core.role.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.role.entity.UmsRole;

/**
 *
 * Date:2016年11月23日 上午10:54:30
 * Version:1.0
 */
public interface UmsRoleRepository extends BaseRepository<UmsRole,String> {
	@Query("select o from UmsRole o where o.sn=?1")
	public UmsRole findByRoleSn(String roleSn);
	
	public void deleteByAppIdIn(List<String> appIds);
}
