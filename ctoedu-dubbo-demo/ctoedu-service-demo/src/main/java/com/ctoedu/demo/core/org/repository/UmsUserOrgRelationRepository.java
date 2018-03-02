package com.ctoedu.demo.core.org.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 上午9:49:54
 * Version:1.0
 */
public interface UmsUserOrgRelationRepository extends BaseRepository<UmsUserOrgRelation,String>{
    @Query("select a.org.id from UmsUserOrgRelation a,UmsOrg b where a.org.id=b.id and a.user.id=?1 and b.isAvailable=?2")
    List<String> findOrgIds(String userId, Short isAvailable);
    
    UmsUserOrgRelation findByOrgIdAndUserId(String orgId, String userId);
    
    @Query("select a.user.id from UmsUserOrgRelation a where a.org.id=?1")
    List<String> findUserIdByOrgId(String orgId);
    
    @Query("select b from UmsUserOrgRelation a,UmsUser b where a.user.id = b.id and a.org.id=?1 and b.nickname like %?2% and b.username like %?3% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByOrgId(String orgId, String nickname, String username, Pageable page);
    
//    @Query("select b from UmsUserOrgRelation a,UmsUser b where a.userId != b.id and a.orgId=?1 and b.nickname like %?2% and b.username like %?3% order by b.createDate DESC")
    @Query("select u from UmsUser u where not exists (select a.user.id from UmsUserOrgRelation a where a.user.id = u.id and a.org.id=?1)  and u.nickname like %?2% and u.username like %?3% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByOrgId(String orgId, String nickname, String username, Pageable page);
    
    @Query("select b from UmsUserOrgRelation a,UmsUser b,UmsPerson c where a.user.id = b.id and b.person.id = c.id and a.org.id=?1 and c.name like %?2% and c.sn like %?3% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByOrgIdAndNameAndSn(String orgId, String name, String sn, Pageable page);
    
//    @Query("select b from UmsUserOrgRelation a,UmsUser b where a.userId != b.id and a.orgId=?1 and b.nickname like %?2% and b.username like %?3% order by b.createDate DESC")
    @Query("select u from UmsUser u, UmsPerson p where not exists (select a.user.id from UmsUserOrgRelation a where a.user.id = u.id and a.org.id=?1)  and u.person.id = p.id and p.name like %?2% and p.sn like %?3% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByOrgIdAndNameAndSn(String orgId, String name, String sn, Pageable page);

    @Modifying
    @Query("delete from UmsUserOrgRelation where org.id=?1")
    public void clearUserOrgRelation(String orgId);
    
    @Modifying
    @Query("delete from UmsUserOrgRelation where user.id=?1")
    public void clearUserOrgRelationByUserId(String userId);
    
    @Modifying
    @Query("delete from UmsUserOrgRelation where org.id=?1 and user.id in ?2")
    public void clearUserOrgRelation(String orgId, List<String> userIds);

    @Modifying
    @Query("delete from UmsUserOrgRelation where org.id=?1 and user.id=?2")
    public void deleteUserOrgRelation(String orgId, String userId);
}
