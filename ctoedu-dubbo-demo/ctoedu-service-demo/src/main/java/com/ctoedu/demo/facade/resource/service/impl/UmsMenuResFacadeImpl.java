package com.ctoedu.demo.facade.resource.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.app.service.UmsAppService;
import com.ctoedu.demo.core.resource.service.UmsButtonService;
import com.ctoedu.demo.core.resource.service.UmsControllerResService;
import com.ctoedu.demo.core.resource.service.UmsMenuResService;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.resource.entity.UmsButton;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;
import com.ctoedu.demo.facade.resource.exception.MenuSnExistsException;
import com.ctoedu.demo.facade.resource.exception.ResourceException;
import com.ctoedu.demo.facade.resource.service.UmsMenuResFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;

@Service(retries=2)
public class UmsMenuResFacadeImpl implements UmsMenuResFacade {
	
	@Autowired
	private UmsMenuResService umsMenuResService;
	@Autowired
	private UmsControllerResService umsControllerResService;
	@Autowired
	private UmsButtonService umsButtonService;
	@Autowired
	private UmsAppService umsAppService;

	@Override
	public UmsMenuResources create(UmsMenuResources umsMenuResources) throws MenuSnExistsException {
		UmsApp app  = umsMenuResources.getApplication();
		String appId = null;
		if(app != null) appId = app.getId();
		UmsMenuResources parent = umsMenuResources.getParent();
		String parentId = null;
		if(parent != null) parentId = parent.getId();
		if(appId != null){
			UmsApp _app = umsAppService.findOne(appId);
			if(_app == null){
				throw new ResourceException("menu.app.not.exited", null);
			}
		}
		if(parentId == null) {
			if(appId == null){
				throw new ResourceException("menu.app.is.null", null);
			}
		}else{
			UmsMenuResources _parent = umsMenuResService.findOne(parentId);
			if(_parent == null){
				throw new ResourceException("menu.parent.not.exited", null);
			} else {
				if(appId == null){
					UmsApp parentApp = _parent.getApplication();
					if(parentApp != null) {
						appId = parentApp.getId();
						app = new UmsApp();
						app.setId(appId);
						umsMenuResources.setApplication(app);
					}
				}
			}
		}
		return umsMenuResService.saveMenu(umsMenuResources);
	}

	@Override
	public UmsMenuResources update(UmsMenuResources umsMenuResources) {
		String id = umsMenuResources.getId();
		UmsMenuResources oMenu = umsMenuResService.findOne(id);
		UmsApp oApp = oMenu.getApplication();
		UmsApp cApp = umsMenuResources.getApplication();
		if(oApp != null && cApp != null && !(oApp.getId().equals(cApp.getId()))){
			updateChildren(id, cApp);
		}
		return umsMenuResService.update(umsMenuResources);
	}
	
	private void updateChildren(String id, UmsApp app){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("parent.id_eq", id);
		List<UmsMenuResources> umrs = umsMenuResService.findAllWithNoPageNoSort(searchable);
		for(UmsMenuResources menu : umrs){
			menu.setApplication(app);
			umsMenuResService.update(menu);
			Searchable cSearchable = Searchable.newSearchable();
			cSearchable.addSearchParam("parent.id_eq", id);
			List<UmsControllerResources> ucrs = umsControllerResService.findAllWithNoPageNoSort(cSearchable);
			for(UmsControllerResources ucr : ucrs){
				ucr.setApplication(app);
				umsControllerResService.update(ucr);
			}
			updateChildren(id, app);
		}
	}

	@Override
	public void deleteMenu(String... ids) {
		umsMenuResService.deleteMenu(ids);
	}

	@Override
	public void assignMenuButton(String menuId, String buttonIds) {
		umsMenuResService.addMenuButton(menuId, buttonIds);
	}
	
	@Override
	public Set<UmsMenuResources> getMenusByUserAndAppSn(UmsUser user,String appSn,Short isAvaiable){
		return umsMenuResService.getMenusByUserAndAppSn(user, appSn, isAvaiable);
	}

	@Override
	public List<UmsMenuResources> listUmsMenuResources(Searchable searchable) {
		return umsMenuResService.findAllWithSort(searchable);
	}
	
	@Override
	public Page<UmsMenuResources> listPageUmsMenuResources(Searchable searchable) {
		return umsMenuResService.findAll(searchable);
	}

	@Override
	public UmsButton create(UmsButton umsButton) {
		return umsButtonService.save(umsButton);
	}

	@Override
	public UmsButton update(UmsButton umsButton) {
		return umsButtonService.update(umsButton);
	}

	@Override
	public void deleteButton(String... ids) {
		umsButtonService.deleteButton(ids);
	}

	public UmsMenuResources findById(String id) {
		return umsMenuResService.findOne(id);
	}

	public UmsMenuResources updAvailable(String id, Short isAvailable) {
		UmsMenuResources mr = umsMenuResService.findOne(id);
		mr.setIsAvailable(isAvailable);
		mr = umsMenuResService.update(mr);
		return mr;
	}
}
