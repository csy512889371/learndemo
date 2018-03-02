package com.ctoedu.demo.core.org.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.org.entity.UmsOrgRule;

/**
 *
 * Date:2016年11月23日 上午9:50:36
 * Version:1.0
 */
public interface UmsOrgRuleRepository extends BaseRepository<UmsOrgRule,String>{
	@Query("select o from UmsOrgRule o where o.orgId=?1")
	public UmsOrgRule loadManagerOrg(String orgId);
	@Modifying
	@Query("delete from UmsOrgRule o where o.orgId=?1")
	public void deleteOrgRuleByOrg(String orgId);
}
