package com.ctoedu.demo.core.department.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 上午9:49:54
 * Version:1.0
 */
public interface UmsUserDepartmentRelationRepository extends BaseRepository<UmsUserDepartmentRelation,String>{
    @Query("select a.department.id from UmsUserDepartmentRelation a,UmsDepartment b where a.department.id=b.id and a.user.id=?1 and b.isAvailable=?2")
    List<String> findDepartmentIds(String userId, Short isAvailable);
    
    UmsUserDepartmentRelation findByDepartmentIdAndUserId(String departmentId, String userId);
    
    @Query("select a.user.id from UmsUserDepartmentRelation a where a.department.id=?1")
    List<String> findUserIdByDepartmentId(String departmentId);
    
    @Query("select b from UmsUserDepartmentRelation a,UmsUser b where a.user.id = b.id and a.department.id=?1 and b.nickname like %?2% and b.username like %?3% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByDepartmentId(String departmentId, String nickname, String username, Pageable page);
    
//    @Query("select b from UmsUserDepartmentRelation a,UmsUser b where a.userId != b.id and a.departmentId=?1 and b.nickname like %?2% and b.username like %?3% order by b.createDate DESC")
    @Query("select u from UmsUser u where not exists (select a.user.id from UmsUserDepartmentRelation a where a.user.id = u.id and a.department.id=?1)  and u.nickname like %?2% and u.username like %?3% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByDepartmentId(String departmentId, String nickname, String username, Pageable page);
    
    @Query("select b from UmsUserDepartmentRelation a,UmsUser b, UmsPerson c where a.user.id = b.id and b.person.id = c.id and a.department.id=?1 and c.name like %?2% and c.sn like %?3% and b.isAvailable='1' order by b.createDate DESC")
    Page<UmsUser> findUserByDepartmentIdAndNameAndSn(String departmentId, String name, String sn, Pageable page);
    
//    @Query("select b from UmsUserDepartmentRelation a,UmsUser b where a.userId != b.id and a.departmentId=?1 and b.nickname like %?2% and b.username like %?3% order by b.createDate DESC")
    @Query("select u from UmsUser u, UmsPerson p where not exists (select a.user.id from UmsUserDepartmentRelation a where a.user.id = u.id and a.department.id=?1)  and u.person.id = p.id and p.name like %?2% and p.sn like %?3% and u.isAvailable='1' order by u.createDate DESC")
    Page<UmsUser> findNotUserByDepartmentIdAndNameAndSn(String departmentId, String name, String sn, Pageable page);

    @Modifying
    @Query("delete from UmsUserDepartmentRelation where department.id=?1")
    public void clearUserDepartmentRelation(String departmentId);
    
    @Modifying
    @Query("delete from UmsUserDepartmentRelation where user.id=?1")
    public void clearUserDepartmentRelationByUserId(String userId);
    
    @Modifying
    @Query("delete from UmsUserDepartmentRelation where department.id=?1 and user.id in ?2")
    public void clearUserDepartmentRelation(String departmentId, List<String> userIds);

    @Modifying
    @Query("delete from UmsUserDepartmentRelation where department.id=?1 and user.id=?2")
    public void deleteUserDepartmentRelation(String departmentId, String userId);
}
