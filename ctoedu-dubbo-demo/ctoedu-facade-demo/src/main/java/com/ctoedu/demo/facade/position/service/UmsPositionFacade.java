package com.ctoedu.demo.facade.position.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.position.exception.PositionSnExistsException;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午1:46:48
 * Version:1.0
 */
public interface UmsPositionFacade {
	/**
	 * 添加职位实体信息
	 * @param UmsPosition
	 * @return
	 */
	public UmsPosition create(UmsPosition umsPosition) throws PositionSnExistsException;
	
	/**
	 * 更新职位实体信息
	 * @param UmsPosition
	 * @return
	 */
	public UmsPosition update(UmsPosition umsPosition);
	
	/**
	 * 删除职位实体信息
	 * @param ids
	 */
	public void delete(String... ids);
	
	/**
	 * 根据职位标识符查询职位实体信息
	 * @param sn
	 * @return
	 */
	public UmsPosition getBySn(String sn);
	
	/**
	 * 根据Id查询职位实体信息
	 * @param name
	 * @return
	 */
	public UmsPosition getById(String id);
	
	/**
	 * 分页查询
	 * @param searchable
	 * @return
	 */
	public Page<UmsPosition> listPage(Searchable searchable);
	
	Page<UmsUserPositionRelation> listUmsUserPositionRelationPage(Searchable searchable);
	
	/**
	 * 条件查询
	 * @param searchable
	 * @return
	 */
	public List<UmsPosition> list(Searchable searchable);
	
	List<UmsUserPositionRelation> listUmsUserPositionRelation(Searchable searchable);
	
	/**
	 * 修改是否可用
	 * @param id
	 * @param isAvailable
	 * @return
	 */
	public UmsPosition updAvailable(String id, Short isAvailable);
	
	/**
     * 根据职位id以及用户昵称和用户名匹配获取该职位下的所有用户
     * @param positionId
     * @param nickname
     * @param username
     * @return
     */
	Page<UmsUser> findUserByPositionId(String positionId, String nickname, String username, Pageable page);
	
	/**
     * 根据职位id以及用户昵称和用户名匹配获取不在该职位下的所有用户
     * @param positionId
     * @param nickname
     * @param username
     * @return
     */
	Page<UmsUser> findNotUserByPositionId(String positionId, String nickname, String username, Pageable page);
	
	Page<UmsUser> findUserByPositionIdAndNameAndSn(String positionId, String name, String sn, Pageable page);
    
    Page<UmsUser> findNotUserByPositionIdAndNameAndSn(String positionId, String name, String sn, Pageable page);
	
	/**
	 * 建立用户与职位关系
	 * @param positionId
	 * @param userIds
	 */
	public void buildUserPositionRelation(String positionId, List<String> userIds);
	
	/**
     * 删除用户与职位关系
     * @param positionId
     * @param userIds
     */
	void clearUserPositionRelation(String positionId, List<String> userIds);
}
