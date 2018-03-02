package com.ctoedu.demo.core.role.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 上午10:54:54
 * Version:1.0
 */
public interface UmsUserRoleRelationRepository extends BaseRepository<UmsUserRoleRelation,String> {
	
	@Query("select b.id from UmsUserRoleRelation a,UmsRole b where a.role.id=b.id and a.user.id=?1 and b.isAvailable=?2")
    public List<String> findRoleIds(String userId, Short isAvailable);
	
	@Query("select b from UmsUserRoleRelation a,UmsRole b where a.role.id=b.id and a.user.id=?1 and b.isAvailable=?2")
    public List<UmsRole> findRoles(String userId, Short isAvailable);
	
	@Query("select a from UmsUserRoleRelation a where a.role.id=?1 and a.user.id=?2")
	public UmsUserRoleRelation findByRoleIdAndUserId(String roleId, String userId);
	
    @Modifying
    @Query("delete from UmsUserRoleRelation where role.id=?1")
    public void clearUserRoleRelation(String roleId);
    
    @Modifying
    @Query("delete from UmsUserRoleRelation where user.id=?1")
    public void clearUserRoleRelationByUserId(String userId);

    @Modifying
    @Query("delete from UmsUserRoleRelation where role.id in ?1")
    public void deleteByRoleIds(List<String> roleIds);
    
    @Modifying
    @Query("delete from UmsUserRoleRelation a where a.role.id in (select b.id from UmsRole b where b.appId in ?1)")
    public void deleteByAppIds(List<String> appIds);
    
    @Query("select b from UmsUserRoleRelation a,UmsUser b where a.user.id = b.id and a.role.id=?1 and b.nickname like %?2% and b.username like %?3% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByRoleId(String roleId, String nickname, String username, Pageable page);
    
    @Query("select u from UmsUser u where not exists (select a.user.id from UmsUserRoleRelation a where a.user.id = u.id and a.role.id=?1)  and u.nickname like %?2% and u.username like %?3% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByRoleId(String roleId, String nickname, String username, Pageable page);
    
    @Modifying
    @Query("delete from UmsUserRoleRelation where role.id=?1 and user.id in ?2")
    public void clearUserRoleRelation(String roleId, List<String> userIds);
}
