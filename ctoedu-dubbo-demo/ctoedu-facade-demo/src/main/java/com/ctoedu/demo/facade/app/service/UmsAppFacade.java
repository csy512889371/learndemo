package com.ctoedu.demo.facade.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.app.entity.UmsApp;


/**
 *
 * Date:2016年11月23日 下午1:44:09
 * Version:1.0
 */
public interface UmsAppFacade {
	/**
	 * 系统注册
	 * @param app
	 * @return
	 */
	public UmsApp register(UmsApp app);
	
	/**
	 * 注销系统
	 * @param app
	 */
	public void delete(List<String> ids);
	
	/**
	 * 修改系统信息
	 * @param app
	 * @return
	 */
	public UmsApp update(UmsApp app);
	
	/**
	 * 根据系统标识符查询系统实体信息
	 * @param sn
	 * @return
	 */
	public UmsApp getBySn(String sn);
	
	/**
	 * 根据Id查询系统实体信息
	 * @param name
	 * @return
	 */
	public UmsApp getById(String id);
	
	/**
	 * 分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsApp> listPage(Searchable searchable);
	
	Page<UmsApp> listPage(String name, String userId, Pageable page);
	
	Page<UmsApp> listPage(String name, String userId, String roleSn, Pageable page);

	Page<UmsApp> listPage(String name, String userId, Short isAvailable, Pageable page);
	
	Page<UmsApp> listPage(String name, String userId, Short isAvailable, String roleSn, Pageable page);
	
	/**
	 * 条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsApp> list(Searchable searchable);
	
	List<UmsApp> findAppByUserRoleRelation(String userId, Short isAvailable);
	
	List<UmsApp> findAppByUserRoleRelation(String userId, String roleSn);
	
	List<UmsApp> findAppByUserRoleRelation(String userId, Short isAvailable, String roleSn);
	
	List<UmsApp> list(String name, String userId, String roleSn);
	
	List<UmsApp> list(String name, String userId, Short isAvailable, String roleSn);
}
