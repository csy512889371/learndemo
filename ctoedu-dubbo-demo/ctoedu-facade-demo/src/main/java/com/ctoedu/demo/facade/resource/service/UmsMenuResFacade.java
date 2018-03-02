package com.ctoedu.demo.facade.resource.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.resource.entity.UmsButton;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午1:46:48
 * Version:1.0
 */
public interface UmsMenuResFacade {
	
	/**
	 * 添加菜单资源实体信息
	 * @param umsMenuResources
	 * @return
	 */
	public UmsMenuResources create(UmsMenuResources umsMenuResources);
	
	/**
	 * 更新菜单资源实体信息
	 * @param umsMenuResources
	 * @return
	 */
	public UmsMenuResources update(UmsMenuResources umsMenuResources);
	
	/**
	 * 删除菜单资源实体信息
	 * @param umsMenuResources
	 * @return
	 */
	public void deleteMenu(String... ids);
	
	/**
	 * 分配菜单按钮
	 * @param menuId
	 * @param buttonIds
	 */
	public void assignMenuButton(String menuId, String buttonIds);
	
	/**
	 * 根据用户、应用系统Sn和菜单状态获取菜单资源集合
	 * @param user
	 * @param appSn
	 * @param isAvaiable
	 * @return
	 */
	public Set<UmsMenuResources> getMenusByUserAndAppSn(UmsUser user, String appSn, Short isAvaiable);
	
	/**
	 * 菜单资源条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsMenuResources> listUmsMenuResources(Searchable searchable);

	/**
	 * 分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsMenuResources> listPageUmsMenuResources(Searchable searchable);
	
	/**
	 * 添加按钮实体信息
	 * @param umsButton
	 * @return
	 */
	public UmsButton create(UmsButton umsButton);
	
	/**
	 * 更新按钮实体信息
	 * @param umsButton
	 * @return
	 */
	public UmsButton update(UmsButton umsButton);
	
	/**
	 * 删除按钮实体信息
	 * @param ids
	 * @return
	 */
	public void deleteButton(String... ids);
	
	/**
	 * 根据ID获取菜单资源
	 * @param id
	 * @return
	 */
	public UmsMenuResources findById(String id);
	
	/**
	 * 修改是否可用
	 * @param id
	 * @param isAvailable
	 * @return
	 */
	public UmsMenuResources updAvailable(String id, Short isAvailable);
}
