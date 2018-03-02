package com.ctoedu.demo.core.resource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;

/**
 *
 * Date:2016年11月23日 上午10:52:44
 * Version:1.0
 */
public interface UmsMenuResRepository extends BaseRepository<UmsMenuResources,String> {
	@Query("select b from UmsAcl a,UmsMenuResources b where a.rid=b.id and a.rtype=?1 and (a.aclState is null or a.aclState=0 or a.aclState>0) and a.pid in ?2 and a.ptype=?3 and b.application.sn=?4 and b.isAvailable=?5") 
	public List<UmsMenuResources> getMenuList(String rtype, List<String> pid, String ptype, String appSn, Short isAvailable);
	@Query("select o from UmsMenuResources o where o.application.sn=?1 and o.isAvailable=?2") 
	public List<UmsMenuResources> getMenusByAppSn(String appSn, Short isAvailable);
	@Query("select o from UmsMenuResources o where o.menuSn=?1 and o.application=?2")
	public UmsMenuResources findMenuByMenuSnAndAppSn(String menuSn, UmsApp app);
	@Query("select o from UmsMenuResources o where o.id in (?1)") 
	public List<UmsMenuResources> findMenuByIds(String menuIds);
	
	@Query("select o.id from UmsMenuResources o where o.parent.id=?1") 
	public List<String> findMenuByParentId(String parentId);
	
	@Modifying
    @Query("delete from UmsMenuResources where application.id in ?1")
    void deleteByAppIds(List<String> appIds);
}
