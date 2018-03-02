package com.ctoedu.demo.core.resource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.resource.entity.UmsMenuButton;

/**
 *
 * Date:2016年11月23日 上午10:52:44
 * Version:1.0
 */
public interface UmsMenuButtonRepository extends BaseRepository<UmsMenuButton,String> {
	@Query("select o.menuId from UmsMenuButton o where o.buttonId=?1")
	public List<String> findMenuIdByButtonId(String buttonId);
	@Modifying
	@Query("delete from UmsMenuButton o where o.menuId=?1")
	public void deleteMenuButtonByMenuId(String menuId);
	@Modifying
	@Query("delete from UmsMenuButton o where o.buttonId=?1")
	public void deleteMenuButtonByButtonId(String menuId);
	
}
