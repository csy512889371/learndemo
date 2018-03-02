package com.ctoedu.demo.facade.role.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.facade.role.exception.RoleSnExistsException;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午2:07:37
 * Version:1.0
 */
public interface UmsRoleFacade {
	/**
	 * 添加角色实体信息
	 * @param umsRole
	 * @return
	 */
	public UmsRole create(UmsRole umsRole) throws RoleSnExistsException;
	
	/**
	 * 更新角色实体信息
	 * @param umsRole
	 * @return
	 */
	public UmsRole update(UmsRole umsRole);
	
	/**
	 * 删除角色实体信息
	 * @param ids
	 */
	public void delete(String... ids);
	
	/**
	 * 删除角色实体信息
	 * @param appIds
	 */
	public void deleteByApp(List<String> appIds);
	
	/**
	 * 根据角色标识符查询角色实体信息
	 * @param sn
	 * @return
	 */
	public UmsRole getBySn(String sn);
	
	/**
	 * 根据Id查询角色实体信息
	 * @param name
	 * @return
	 */
	public UmsRole getById(String id);
	
	/**
	 * 分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsRole> listPage(Searchable searchable);
	
	Page<UmsUserRoleRelation> listUmsUserRoleRelationPage(Searchable searchable);
	
	/**
	 * 条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsRole> list(Searchable searchable);
	
	List<UmsUserRoleRelation> listUmsUserRoleRelation(Searchable searchable);
	
	/**
	 * 修改是否可用
	 * @param id
	 * @param isAvailable
	 * @return
	 */
	public UmsRole updAvailable(String id, Short isAvailable);
	
	/**
	 * 添加用户角色关联
	 * @param roleId
	 * @param userIds
	 */
	void buildUserRoleRelation(String roleId, String[] userIds);
	
	/**
     * 删除用户与角色关系
     * @param roleId
     * @param userIds
     */
	void clearUserRoleRelation(String roleId, List<String> userIds);
	
	/**
     * 根据角色id获取该角色下的所有用户
     * @param roleId
     * @param nickname
     * @param username
     * @param page
     * @return
     */
	Page<UmsUser> findUserByRoleId(String roleId, String nickname, String username, Pageable page);
	
	/**
     * 根据角色id获取该角色下以外的所有用户
     * @param roleId
     * @param nickname
     * @param username
     * @param page
     * @return
     */
	Page<UmsUser> findNotUserByRoleId(String roleId, String nickname, String username, Pageable page);
}
