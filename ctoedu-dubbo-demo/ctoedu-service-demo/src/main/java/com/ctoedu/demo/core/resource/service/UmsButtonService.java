package com.ctoedu.demo.core.resource.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.auth.repository.UmsAclRepository;
import com.ctoedu.demo.core.resource.repository.UmsButtonRepository;
import com.ctoedu.demo.core.resource.repository.UmsMenuButtonRepository;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.auth.entity.UmsAcl;
import com.ctoedu.demo.facade.resource.entity.UmsButton;

/**
 *
 * Date:2016年11月23日 下午3:38:28
 * Version:1.0
 */
@Service("umsButtonService")
public class UmsButtonService extends BaseService<UmsButton, String>{
	@Autowired
	private UmsButtonRepository umsButtonRepository;
	@Autowired
	private UmsMenuButtonRepository umsMenuButtonRepository;
	@Autowired
	private UmsAclRepository umsAclRepository;
	/**
	 * 根据菜单获取所有可用按钮
	 * @param menuId
	 * @return
	 */
	public List<UmsButton> findButtonsByMenuId(String menuId,Short isAvailable){
		return umsButtonRepository.findButtonsByMenuId(menuId,isAvailable);
	}
	
	/**
	 * 删除按钮
	 * @param ids
	 */
	public void deleteButton(String... ids){
		for(String id:ids){
    		UmsButton umsButton = umsButtonRepository.findOne(id);
    		List<String> menuIdList = umsMenuButtonRepository.findMenuIdByButtonId(id);
    		if(menuIdList!=null&&menuIdList.size()>0){
    			for(String menuId:menuIdList){
    				List<UmsAcl> acls = umsAclRepository.getAclByResource(menuId, Resource.RESOURCE_MENU);
    				for(UmsAcl acl:acls){
    					acl.setPermission(umsButton.getButtonOrder(), false);
    					umsAclRepository.save(acl);
    				}
    			}
        		umsMenuButtonRepository.deleteMenuButtonByButtonId(id);
    		}
			delete(id);
		}
	}
}
