package com.ctoedu.demo.core.orgRole.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;

/**
 *
 * Date:2016年11月23日 上午10:54:30
 * Version:1.0
 */
public interface UmsOrgRoleRepository extends BaseRepository<UmsOrgRole,String> {
	@Query("select o from UmsOrgRole o where o.sn=?1")
	public UmsOrgRole findByRoleSn(String roleSn);
	
	public void deleteByOrgIdIn(String[] orgIds);
	
	List<UmsOrgRole> findByOrgId(String orgId);
}
