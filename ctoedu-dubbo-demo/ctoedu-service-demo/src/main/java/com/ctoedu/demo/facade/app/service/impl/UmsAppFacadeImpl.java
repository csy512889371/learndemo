package com.ctoedu.demo.facade.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.app.service.UmsAppService;
import com.ctoedu.demo.core.resource.service.UmsControllerResService;
import com.ctoedu.demo.core.resource.service.UmsMenuResService;
import com.ctoedu.demo.core.role.service.UmsRoleService;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.exception.AppSnExistsException;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;

@Service(retries=2,interfaceName="com.ctoedu.demo.facade.app.service.UmsAppFacade")
public class UmsAppFacadeImpl implements UmsAppFacade {
	
	@Autowired
	private UmsAppService umsAppService;
	
	@Autowired
	private UmsRoleService umsRoleService;

	@Autowired
	private UmsControllerResService umsControllerResService;

	@Autowired
	private UmsMenuResService umsMenuResService;

	@Override
	public UmsApp register(UmsApp app) throws AppSnExistsException {
		return umsAppService.saveApp(app);
	}

	@Override
	public void delete(List<String> ids) {
		umsRoleService.deleteByAppIdIn(ids);
		umsControllerResService.deleteByAppIds(ids);
		umsMenuResService.deleteByAppIds(ids);
		umsAppService.delete(ids.toArray(new String[ids.size()]));
	}

	@Override
	public UmsApp update(UmsApp app) {
		return umsAppService.update(app);
	}

	@Override
	public UmsApp getBySn(String sn) {
		return umsAppService.findAppBySn(sn);
	}

	@Override
	public UmsApp getById(String id) {
		return umsAppService.findOne(id);
	}

	@Override
	public Page<UmsApp> listPage(Searchable searchable) {
		return umsAppService.findAll(searchable);
	}

	@Override
	public List<UmsApp> list(Searchable searchable) {
		return umsAppService.findAllWithNoPageNoSort(searchable);
	}

	@Override
	public List<UmsApp> findAppByUserRoleRelation(String userId, Short isAvailable) {
		return umsAppService.findAppByUserRoleRelation(userId, isAvailable);
	}
	
	@Override
	public Page<UmsApp> listPage(String name, String userId, Pageable page) {
		return umsAppService.listPage(name, userId, page);
	}

	@Override
	public Page<UmsApp> listPage(String name, String userId, Short isAvailable, Pageable page) {
		return umsAppService.listPage(name, userId, isAvailable, page);
	}

	@Override
	public Page<UmsApp> listPage(String name, String userId, Short isAvailable, String roleSn, Pageable page) {
		return umsAppService.listPage(name, userId, isAvailable, roleSn, page);
	}

	@Override
	public Page<UmsApp> listPage(String name, String userId, String roleSn, Pageable page) {
		return umsAppService.listPage(name, userId, roleSn, page);
	}
	
	@Override
	public List<UmsApp> findAppByUserRoleRelation(String userId, String roleSn) {
		return umsAppService.findAppByUserRoleRelation(userId, roleSn);
	}

	@Override
	public List<UmsApp> findAppByUserRoleRelation(String userId, Short isAvailable, String roleSn) {
		return umsAppService.findAppByUserRoleRelation(userId, isAvailable, roleSn);
	}
	
	@Override
	public List<UmsApp> list(String name, String userId, String roleSn) {
		return umsAppService.list(name, userId, roleSn);
	}

	@Override
	public List<UmsApp> list(String name, String userId, Short isAvailable, String roleSn) {
		return umsAppService.list(name, userId, isAvailable, roleSn);
	}
}
