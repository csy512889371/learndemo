package com.ctoedu.demo.core.resource.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.auth.repository.UmsAclRepository;
import com.ctoedu.demo.core.resource.repository.UmsControllerOperRepository;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.auth.entity.UmsAcl;
import com.ctoedu.demo.facade.resource.entity.UmsControllerOper;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.exception.ControllerSnExistsException;

@Service
public class UmsControllerOperService extends BaseService<UmsControllerOper, String>{
	@Autowired
	private UmsControllerOperRepository umsControllerOperRepository;
	@Autowired
	private UmsAclRepository umsAclRepository;
	
	/**
	 * 保存控制器操作实体信息
	 * @param menu
	 */
	public UmsControllerOper saveControllerOper(UmsControllerOper umsControllerOper){
		if(findByControllerOperSn(umsControllerOper.getControllerOperSn())!=null){
            throw new ControllerSnExistsException();
        }
        return save(umsControllerOper);
	}
	/**
	 * 根据控制器操作sn和应用系统获取菜单
	 * @param menuSn
	 * @param app
	 * @return
	 */
	public UmsControllerOper findByControllerOperSn(String controllerOperSn){
		return umsControllerOperRepository.findByControllerOperSn(controllerOperSn);
	}

	/**
	 * 删除控制器资源
	 * @param ids
	 */
	public void deleteControllerOper(String... ids){
		for(String id:ids){
			UmsControllerOper umsControllerOper = this.findOne(id);
			UmsControllerResources umsControllerResources = umsControllerOper.getControllerResources();
			if(umsControllerResources!=null){
				List<UmsAcl> acls = umsAclRepository.getAclByResource(umsControllerResources.getId(), Resource.RESOURCE_CONTROLLER);
				for(UmsAcl acl:acls){
					acl.setPermission(umsControllerResources.getControllerOrder(), false);
					umsAclRepository.save(acl);
				}
				delete(id);
    		}
		}
	}
}
