package com.ctoedu.demo.core.org.repository;

import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.org.entity.UmsOrgType;

/**
 *
 * Date:2016年11月23日 上午9:51:27
 * Version:1.0
 */
public interface UmsOrgTypeRepository extends BaseRepository<UmsOrgType,String> {
	@Query("select o from UmsOrgType o where o.sn=?1")
	public UmsOrgType findOrgTypeBySn(String sn);
}
