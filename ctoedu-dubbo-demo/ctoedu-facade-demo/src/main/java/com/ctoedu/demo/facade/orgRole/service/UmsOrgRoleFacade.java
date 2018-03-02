package com.ctoedu.demo.facade.orgRole.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.exception.OrgRoleSnExistsException;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午2:07:37
 * Version:1.0
 */
public interface UmsOrgRoleFacade {
	/**
	 * 添加角色实体信息
	 * @param UmsOrgRole
	 * @return
	 */
	public UmsOrgRole create(UmsOrgRole UmsOrgRole) throws OrgRoleSnExistsException;
	
	/**
	 * 更新角色实体信息
	 * @param UmsOrgRole
	 * @return
	 */
	public UmsOrgRole update(UmsOrgRole UmsOrgRole);
	
	/**
	 * 删除角色实体信息
	 * @param ids
	 */
	public void delete(String... ids);
	
	/**
	 * 删除角色实体信息
	 * @param orgIds
	 */
	public void deleteByOrg(String[] orgIds);
	
	/**
	 * 根据角色标识符查询角色实体信息
	 * @param sn
	 * @return
	 */
	public UmsOrgRole getBySn(String sn);
	
	/**
	 * 根据Id查询角色实体信息
	 * @param name
	 * @return
	 */
	public UmsOrgRole getById(String id);
	
	/**
	 * 分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsOrgRole> listPage(Searchable searchable);
	
	Page<UmsUserOrgRoleRelation> listUmsUserRoleRelationPage(Searchable searchable);
	
	/**
	 * 条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsOrgRole> list(Searchable searchable);
	
	List<UmsUserOrgRoleRelation> listUmsUserRoleRelation(Searchable searchable);
	
	/**
	 * 修改是否可用
	 * @param id
	 * @param isAvailable
	 * @return
	 */
	public UmsOrgRole updAvailable(String id, Short isAvailable);
	
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
	
	Page<UmsUser> findUserByRoleIdAndNameAndSn(String roleId, String name, String sn, Pageable page);
    
    Page<UmsUser> findNotUserByRoleIdAndNameAndSn(String roleId, String name, String sn, Pageable page);
}
