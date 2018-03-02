package com.ctoedu.demo.facade.resource.service;

import java.util.List;
import java.util.Set;

import com.ctoedu.demo.facade.resource.exception.ControllerSnExistsException;
import org.springframework.data.domain.Page;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.resource.entity.UmsControllerOper;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.user.entity.UmsUser;

public interface UmsControllerResFacade {

	/**
	 * 添加控制器资源实体信息
	 * @param umsControllerResources
	 * @return
	 */
	public UmsControllerResources create(UmsControllerResources umsControllerResources) throws ControllerSnExistsException;
	
	/**
	 * 更新控制器资源实体信息
	 * @param umsControllerResources
	 * @return
	 */
	public UmsControllerResources update(UmsControllerResources umsControllerResources);
	
	/**
	 * 删除控制器资源实体信息
	 * @param umsMenuResources
	 * @return
	 */
	public void deleteControllerRes(String... ids);
	
	/**
	 * 根据用户、应用系统Sn获取控制器资源集合
	 * @param user
	 * @param appSn
	 * @return
	 */
	public Set<UmsControllerResources> getControllerResByUserAndAppSn(UmsUser user, String appSn, String menuId, Short isAvaiable);
	
	/**
	 * 控制器资源条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsControllerResources> listUmsControllerRes(Searchable searchable);

	/**
	 * 分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsControllerResources> listPageUmsControllerRes(Searchable searchable);
	
	/**
	 * 添加控制器操作实体信息
	 * @param umsControllerOper
	 * @return
	 */
	public UmsControllerOper create(UmsControllerOper umsControllerOper);
	
	/**
	 * 更新控制器操作实体信息
	 * @param umsControllerOper
	 * @return
	 */
	public UmsControllerOper update(UmsControllerOper umsControllerOper);
	
	/**
	 * 删除控制器操作实体信息
	 * @param ids
	 * @return
	 */
	public void deleteUmsControllerOper(String... ids);
	
	/**
	 * 控制器操作条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsControllerOper> listUmsControllerOper(Searchable searchable);

	/**
	 * 控制器操作分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsControllerOper> listPageUmsControllerOper(Searchable searchable);
	
	/**
	 * 根据ID获取菜单资源
	 * @param id
	 * @return
	 */
	public UmsControllerResources findById(String id);
	
	/**
	 * 修改是否可用
	 * @param id
	 * @param isAvailable
	 * @return
	 */
	public UmsControllerResources updAvailable(String id, Short isAvailable);
	
	public boolean containsUrlMappingForAuth(String url, UmsUser user, String appSn);
}
