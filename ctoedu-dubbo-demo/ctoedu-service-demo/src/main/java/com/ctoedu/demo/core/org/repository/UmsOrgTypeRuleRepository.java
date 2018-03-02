package com.ctoedu.demo.core.org.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.org.entity.UmsOrgTypeRule;

/**
 *
 * Date:2016年11月23日 上午10:03:39
 * Version:1.0
 */
public interface UmsOrgTypeRuleRepository extends BaseRepository<UmsOrgTypeRule,String> {
	@Query("select o.num from UmsOrgTypeRule o where o.pid=?1 and o.cid=?2")
	public Integer loadOrgTypeRuleNum(String pTypeId, String cTypeId);
	@Query("select o from UmsOrgTypeRule o where o.pid=?1 and o.cid=?2")
	public List<UmsOrgTypeRule> listByRule(String pid);
	@Modifying
    @Query("delete from UmsOrgTypeRule where pid=?1")
	public void deleteOrgTypeRuleByOrgType(String pTypeId);
}
