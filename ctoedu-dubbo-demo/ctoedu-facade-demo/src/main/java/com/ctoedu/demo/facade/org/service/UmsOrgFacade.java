package com.ctoedu.demo.facade.org.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsOrgRule;
import com.ctoedu.demo.facade.org.entity.UmsOrgType;
import com.ctoedu.demo.facade.org.entity.UmsOrgTypeRule;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午2:04:24
 * Version:1.0
 */
public interface UmsOrgFacade {
	/**
	 * 添加组织机构
	 * @param umsOrg
	 * @return
	 */
	public UmsOrg createUmsOrg(UmsOrg umsOrg);
	
	/**
	 * 更新组织机构
	 * @param umsOrg
	 * @return
	 */
	public UmsOrg updateUmsOrg(UmsOrg umsOrg);
	
	/**
	 * 删除组织机构
	 * @param ids
	 */
	public void deleteUmsOrg(String... ids);
	
	/**
	 * 组织机构分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsOrg> listUmsOrgPage(Searchable searchable);
	
	Page<UmsUserOrgRelation> listUmsUserOrgRelationPage(Searchable searchable);
	
	/**
	 * 组织机构条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsOrg> listUmsOrg(Searchable searchable);
	
	List<UmsUserOrgRelation> listUmsUserOrgRelation(Searchable searchable);
	
	/**
	 * 添加组织机构规则
	 * @param orgId
	 * @param managerOrg
	 * @return
	 */
	public UmsOrgRule saveUmsOrgRule(String orgId, String managerOrg);
	
	/**
	 * 更新组织机构规则
	 * @param orgId
	 * @param managerOrg
	 * @return
	 */
	public UmsOrgRule updateUmsOrgRule(String orgId, String managerOrg);
	
	/**
	 * 根据主键Id删除组织机构规则
	 * @param ids
	 */
	public void deleteUmsOrgRule(String... ids);
	
	/**
	 * 根据组织机构Id删除组织机构规则
	 * @param orgId
	 */
	public void deleteOrgRuleByOrg(String orgId);
	
    /**
     * 根据组织机构Id获取所管理的组织机构Id集合
     * @param orgId
     * @return
     */
	public List<String> listManagerRuleIds(String orgId);
	
	/**
	 * 添加组织机构类型
	 * @param umsOrgType
	 * @return
	 */
	public UmsOrgType createUmsOrgType(UmsOrgType umsOrgType);
	
	/**
	 * 更新组织机构类型
	 * @param umsOrgType
	 * @return
	 */
	public UmsOrgType updateUmsOrgType(UmsOrgType umsOrgType);
	
	/**
	 * 删除组织机构类型
	 * @param ids
	 */
	public void deleteUmsOrgType(String... ids);
	
	/**
	 * 组织机构类型分页条件查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsOrgType> listUmsOrgTypePage(Searchable searchable);
	
	/**
	 * 条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsOrgType> listUmsOrgType(Searchable searchable);
	
	/**
	 * 添加组织机构类型规则
	 * @param umsOrgTypeRule
	 */
	public UmsOrgTypeRule createOrgTypeRule(UmsOrgTypeRule umsOrgTypeRule);
	
	/**
	 * 更新组织机构类型规则
	 * @param umsOrgTypeRule
	 */
	public UmsOrgTypeRule updateOrgTypeRule(UmsOrgTypeRule umsOrgTypeRule);
	
	/**
	 * 删除组织机构类型规则
	 * @param id
	 */
	public void deleteOrgTypeRule(String... id);
	
	/**
	 * 根据父节点获取子节点
	 * @param pid
	 * @return
	 */
	public List<UmsOrgTypeRule> listOrgTypeRuleChild(String pid);
	
	/**
	 * 修改是否可用
	 * @param id
	 * @param isAvailable
	 * @return
	 */
	public UmsOrg updAvailable(String id, Short isAvailable);
	
	/**
	 * 根据Id查询组织机构实体信息
	 * @param orgId
	 * @return
	 */
	public UmsOrg getById(String orgId);
	
	/**
     * 根据机构id以及用户昵称和用户名匹配获取该机构下的所有用户
     * @param orgId
     * @param nickname
     * @param username
     * @return
     */
	Page<UmsUser> findUserByOrgId(String orgId, String nickname, String username, Pageable page);
	
	/**
     * 根据机构id以及用户昵称和用户名匹配获取不在该机构下的所有用户
     * @param orgId
     * @param nickname
     * @param username
     * @return
     */
	Page<UmsUser> findNotUserByOrgId(String orgId, String nickname, String username, Pageable page);
	
	Page<UmsUser> findUserByOrgIdAndNameAndSn(String orgId, String name, String sn, Pageable page);
	
	Page<UmsUser> findNotUserByOrgIdAndNameAndSn(String orgId, String name, String sn, Pageable page);
	
	/**
	 * 建立用户与机构关系
	 * @param orgId
	 * @param userIds
	 */
	public void buildUserOrgRelation(String orgId, List<String> userIds);
	
	/**
     * 删除用户与机构关系
     * @param orgId
     * @param userIds
     */
	void clearUserOrgRelation(String orgId, List<String> userIds);
	
//	/**
//	 * 建立用户与组织机构下的职位的关系
//	 * @param groupId
//	 * @param positionId
//	 * @param userIds
//	 */
//    void buildUserOrgPositionRelation(String orgId, String positionId, List<String> userIds);
//	
//	/**
//	 * 删除用户与组织机构的职位关系
//	 * @param orgId
//	 * @param positionId
//	 * @param userIds
//	 */
//	void clearUserOrgPositionRelation(String orgId, String positionId, List<String> userIds);
	
//	/**
//	 * 根据组织机构下的职位获取所有用户
//	 * @param orgId
//	 * @param positionId
//	 * @param nickname
//	 * @param username
//	 * @param page
//	 * @return
//	 */
//	Page<UmsUser> findUserByOrgIdAndPositionId(String orgId, String positionId, String nickname, String username, Pageable page);
//	
//	/**
//	 * 根据组织机构下的职位获取所有以外的用户
//	 * @param orgId
//	 * @param positionId
//	 * @param nickname
//	 * @param username
//	 * @param page
//	 * @return
//	 */
//	Page<UmsUser> findNotUserByOrgIdAndPositionId(String orgId, String positionId, String nickname, String username, Pageable page);
}
