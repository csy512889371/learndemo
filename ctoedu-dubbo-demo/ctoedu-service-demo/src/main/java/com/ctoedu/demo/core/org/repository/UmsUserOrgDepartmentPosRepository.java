package com.ctoedu.demo.core.org.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgDepartmentPos;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 上午10:04:07
 * Version:1.0
 */
public interface UmsUserOrgDepartmentPosRepository extends BaseRepository<UmsUserOrgDepartmentPos,String> {
	@Query("select a.orgId from UmsUserOrgDepartmentPos a,UmsOrg b where a.orgId=b.id and a.userId=?1 and b.isAvailable=?2")
    List<String> findOrgIds(String userId, Short isAvailable);
	
	@Query("select a.posId from UmsUserOrgDepartmentPos a,UmsPosition b where a.posId=b.id and a.userId=?1 and b.isAvailable=?2")
    List<String> findPosIds(String userId, Short isAvailable);
	
//	@Query("select o from UmsUserOrgDepartmentPos o where o.userId=?1")
//	UmsUserOrgDepartmentPos findUmsUserOrgDepartmentPosByPersonId(String userId);
	
	@Modifying
	@Query("update UmsUserOrgDepartmentPos o set o.posId=?1 where o.posId=?2")
	void clearUserPosRelation(String newPosId, String oldPosId);
	
	@Modifying
	@Query("update UmsUserOrgDepartmentPos o set o.orgId=?1 where o.orgId=?2")
	void clearUserOrgRelation(String newOrgId, String oldOrgId);
	
	@Modifying
    @Query("delete from UmsUserOrgDepartmentPos where orgId=?1 and posId=?2 and userId in ?3")
    void clearUserOrgPositionRelation(String orgId, String positionId, List<String> userIds);
	
	@Modifying
    @Query("delete from UmsUserOrgDepartmentPos where orgId=?1 and posId is null and userId in ?2")
    void clearUserOrgPositionRelation(String orgId, List<String> userIds);
	
	@Query("select b from UmsUserOrgDepartmentPos a,UmsUser b where a.userId = b.id and a.orgId=?1 and a.posId=?2 and b.nickname like %?3% and b.username like %?4% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByOrgIdAndPositionId(String orgId, String positionId, String nickname, String username, Pageable page);
	
	@Query("select b from UmsUserOrgDepartmentPos a,UmsUser b where a.userId = b.id and a.orgId=?1 and a.posId is null and b.nickname like %?2% and b.username like %?3% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByOrgIdAndPositionId(String orgId, String nickname, String username, Pageable page);
    
    @Query("select u from UmsUser u where not exists (select a.userId from UmsUserOrgDepartmentPos a where a.userId = u.id and a.orgId=?1 and a.posId=?2)  and u.nickname like %?3% and u.username like %?4% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByOrgIdAndPositionId(String orgId, String positionId, String nickname, String username, Pageable page);
    
    @Query("select u from UmsUser u where not exists (select a.userId from UmsUserOrgDepartmentPos a where a.userId = u.id and a.orgId=?1 and a.posId is null)  and u.nickname like %?2% and u.username like %?3% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByOrgIdAndPositionId(String orgId, String nickname, String username, Pageable page);
}
