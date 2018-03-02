package com.ctoedu.demo.facade.department.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午1:44:55
 * Version:1.0
 */
public interface UmsDepartmentFacade {
	/**
	 * 添加组实体信息
	 * @param umsGroup
	 * @return
	 */
	public UmsDepartment create(UmsDepartment umsDepartment);
	
	/**
	 * 更新组实体信息
	 * @param umsGroup
	 * @return
	 */
	public UmsDepartment update(UmsDepartment umsDepartment);
	
	/**
	 * 删除组信息
	 * @param umsGroup
	 */
	public void delete(String... departmentIds);
	
	/**
	 * 根据Id查询组实体信息
	 * @param groupId
	 * @return
	 */
	public UmsDepartment getById(String departmentId);
	
	/**
	 * 分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsDepartment> listPage(Searchable searchable);
	
	Page<UmsUserDepartmentRelation> listUmsUserDepartmentRelationPage(Searchable searchable);
	
	/**
	 * 条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsDepartment> list(Searchable searchable);
	
	List<UmsUserDepartmentRelation> listUmsUserDepartmentRelation(Searchable searchable);
	
	/**
	 * 根据用户id查询用户所属组id集合
	 * @param userId
	 * @param isAvailable
	 * @return
	 */
	public Set<String> findDepartmentIds(String userId, Short isAvailable);
	
	/**
	 * 建立用户与组关系
	 * @param groupId
	 * @param userIds
	 */
	public void buildUserDepartmentRelation(String departmentId, List<String> userIds);
	
	/**
     * 删除用户与组关系
     * @param groupId
     * @param userIds
     */
	void clearUserDepartmentRelation(String departmentId, List<String> userIds);
	
	void deleteUserDepartmentRelation(String departmentId, List<String> userIds);
	
	/**
	 * 修改是否可用
	 * @param id
	 * @param isAvailable
	 * @return
	 */
	public UmsDepartment updAvailable(String id, Short isAvailable);
	
	/**
     * 根据组id获取该组下的所有用户id
     * @param groupId
     * @return
     */
	List<String> findUserIdByDepartmentId(String departmentId);
	
	/**
     * 根据组id以及用户昵称和用户名匹配获取该组下的所有用户
     * @param groupId
     * @param nickname
     * @param username
     * @return
     */
	Page<UmsUser> findUserByDepartmentId(String departmentId, String nickname, String username, Pageable page);
	
	/**
     * 根据组id以及用户昵称和用户名匹配获取不在该组下的所有用户
     * @param groupId
     * @param nickname
     * @param username
     * @return
     */
	Page<UmsUser> findNotUserByDepartmentId(String departmentId, String nickname, String username, Pageable page);
	
	Page<UmsUser> findUserByDepartmentIdAndNameAndSn(String departmentId, String name, String sn, Pageable page);
	
	Page<UmsUser> findNotUserByDepartmentIdAndNameAndSn(String departmentId, String name, String sn, Pageable page);
}
