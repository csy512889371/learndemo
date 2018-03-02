package com.ctoedu.demo.core.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.app.entity.UmsApp;

public interface UmsAppRepository extends BaseRepository<UmsApp,String> {
	@Query("select o from UmsApp o where o.sn=?1")
	public UmsApp findAppBySn(String appSn);
	
	@Query("select o from UmsApp o where o.name=?1")
	public UmsApp findAppByName(String name);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and u.user.id=?1 and r.isAvailable=?2")
	public List<UmsApp> findAppByUserRoleRelation(String userId, Short isAvailable);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and u.user.id=?1 and r.sn like ?2%")
	public List<UmsApp> findAppByUserRoleRelation(String userId, String roleSn);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and u.user.id=?1 and r.isAvailable=?2 and r.sn like ?3%")
	public List<UmsApp> findAppByUserRoleRelation(String userId, Short isAvailable, String roleSn);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and o.name like %?1% and u.user.id=?2 and r.sn like ?3%")
	public List<UmsApp> findAppByUserRoleRelation(String name, String userId, String roleSn);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and o.name like %?1% and u.user.id=?2 and r.isAvailable=?3 and r.sn like ?4%")
	public List<UmsApp> findAppByUserRoleRelation(String name, String userId, Short isAvailable, String roleSn);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and o.name like %?1% and u.user.id=?2")
	public Page<UmsApp> findAppByUserRoleRelation(String name, String userId, Pageable page);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and o.name like %?1% and u.user.id=?2 and r.sn like ?3%")
	public Page<UmsApp> findAppByUserRoleRelation(String name, String userId, String roleSn, Pageable page);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and o.name like %?1% and u.user.id=?2 and r.isAvailable=?3")
	public Page<UmsApp> findAppByUserRoleRelation(String name, String userId, Short isAvailable, Pageable page);
	
	@Query("select o from UmsApp o, UmsRole r, UmsUserRoleRelation u where o.id=r.appId and r.id=u.role.id and o.name like %?1% and u.user.id=?2 and r.isAvailable=?3 and r.sn like ?4%")
	public Page<UmsApp> findAppByUserRoleRelation(String name, String userId, Short isAvailable, String roleSn, Pageable page);
}
