package com.ctoedu.demo.core.orgRole.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 上午10:54:54
 * Version:1.0
 */
public interface UmsUserOrgRoleRelationRepository extends BaseRepository<UmsUserOrgRoleRelation,String> {
	
	@Query("select b.id from UmsUserOrgRoleRelation a,UmsOrgRole b where a.role.id=b.id and a.user.id=?1 and b.isAvailable=?2")
    public List<String> findRoleIds(String userId, Short isAvailable);
	
	@Query("select b from UmsUserOrgRoleRelation a,UmsOrgRole b where a.role.id=b.id and a.user.id=?1 and b.isAvailable=?2")
    public List<UmsOrgRole> findRoles(String userId, Short isAvailable);
	
	@Query("select a from UmsUserOrgRoleRelation a where a.role.id=?1 and a.user.id=?2")
	public UmsUserOrgRoleRelation findByRoleIdAndUserId(String roleId, String userId);
	
    @Modifying
    @Query("delete from UmsUserOrgRoleRelation where role.id=?1")
    public void clearUserRoleRelation(String roleId);
    
    @Query("select b from UmsUserOrgRoleRelation a,UmsUser b where a.user.id = b.id and a.role.id=?1 and b.nickname like %?2% and b.username like %?3% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByRoleId(String roleId, String nickname, String username, Pageable page);
    
    @Query("select u from UmsUser u where not exists (select a.user.id from UmsUserOrgRoleRelation a where a.user.id = u.id and a.role.id=?1)  and u.nickname like %?2% and u.username like %?3% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByRoleId(String roleId, String nickname, String username, Pageable page);
    
    @Query("select b from UmsUserOrgRoleRelation a,UmsUser b,UmsPerson c where a.user.id = b.id and b.person.id = c.id and a.role.id=?1 and c.name like %?2% and c.sn like %?3% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByRoleIdAndNameAndSn(String roleId, String name, String sn, Pageable page);
    
    @Query("select u from UmsUser u, UmsPerson p where not exists (select a.user.id from UmsUserOrgRoleRelation a where a.user.id = u.id and a.role.id=?1) and u.person.id = p.id and p.name like %?2% and p.sn like %?3% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByRoleIdAndNameAndSn(String roleId, String name, String sn, Pageable page);
    
    @Modifying
    @Query("delete from UmsUserOrgRoleRelation where role.id=?1 and user.id in ?2")
    public void clearUserRoleRelation(String roleId, List<String> userIds);
    
    @Modifying
    @Query("delete from UmsUserOrgRoleRelation where user.id=?1")
    public void clearUserRoleRelationByUserId(String userId);
}
