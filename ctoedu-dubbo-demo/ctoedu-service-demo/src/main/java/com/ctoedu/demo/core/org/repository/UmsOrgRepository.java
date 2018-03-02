package com.ctoedu.demo.core.org.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.org.entity.UmsOrg;

/**
 * 
 * repository接口.
 * Date:2016-11-09 22:40
 * User:chenxiang
 * Version:1.0
 *
 */
public interface UmsOrgRepository extends BaseRepository<UmsOrg, String> {
	@Query("select count(*) from UmsOrg o where o.typeId=?1 and o.id=?2")
	public Integer getOrgNumsByType(String typeId, String pId);
	@Query("select count(*) from UmsOrg o where o.typeId=?1")
	public Integer getOrgNumsByType(String orgTypeId);
	@Query("select o.orderNum from UmsOrg o where o.id=?1")
	public Integer getMaxOrder(String pid);
	@Query("select o from UmsOrg o where o.parent.id=?1")
	List<UmsOrg> findByParentId(String parentId);
}