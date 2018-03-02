package com.ctoedu.demo.core.resource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.resource.entity.UmsButton;

/**
 *
 * Date:2016年11月23日 上午10:52:44
 * Version:1.0
 */
public interface UmsButtonRepository extends BaseRepository<UmsButton,String> {

	@Query("select a from UmsButton a,UmsMenuButton b where a.id=b.buttonId and b.menuId=?1 and a.isAvailable=?2")
	public List<UmsButton> findButtonsByMenuId(String menuId, Short isAvailable);
}
