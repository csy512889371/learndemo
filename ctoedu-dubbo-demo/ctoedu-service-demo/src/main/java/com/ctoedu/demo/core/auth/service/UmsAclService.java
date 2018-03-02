package com.ctoedu.demo.core.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.auth.repository.UmsAclRepository;
import com.ctoedu.demo.core.resource.repository.UmsButtonRepository;
import com.ctoedu.demo.core.resource.repository.UmsControllerOperRepository;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.auth.entity.UmsAcl;
import com.ctoedu.demo.facade.resource.entity.UmsButton;
import com.ctoedu.demo.facade.resource.entity.UmsControllerOper;

/**
 *
 * Date:2016年11月23日 下午3:37:45
 * Version:1.0
 */
@Service("umsAclService")
public class UmsAclService extends BaseService<UmsAcl, String>{
	@Autowired
	private UmsAclRepository umsAclRepository;
	@Autowired
	private UmsButtonRepository umsButtonRepository;
	@Autowired
	private UmsControllerOperRepository umsControllerOperRepository;
    
    /**
     * 清除主体资源
     * @param pid
     * @param ptype
     */
    public void clearPrincipalResourceAcl(String pid, String ptype, String rtype){
    	umsAclRepository.clearPrincipalResourceAcl(pid,ptype,rtype);
    }
    
    /**
     * 添加资源权限
     * @param pid
     * @param ptype
     * @param rids
     * @param rtype
     * @param map
     */
    public void addPrincipalAcl(String pid, String ptype, String[] rids, String rtype, Map<String,String[]> map){
    	if(Resource.RESOURCE_MENU.equals(rtype)){
    		addPrincipalMenuAcl(pid, ptype, rids, rtype, map);
    	}else if(Resource.RESOURCE_CONTROLLER.equals(rtype)){
    		addPrincipalControllerAcl(pid, ptype, rids, rtype, map);
    	}
    }
    
    /**
     * 添加菜单和按钮权限
     * @param pid
     * @param ptype
     * @param rids
     * @param rtype
     * @param map
     */
    private void addPrincipalMenuAcl(String pid, String ptype, String[] rids, String rtype, Map<String,String[]> map){
    	//移除未分配的菜单
    	if(rids == null || rids.length == 0){
    		umsAclRepository.clearPrincipalResourceAcl(pid, ptype, rtype);
    	}else{
    		
        	umsAclRepository.removeUnAssignedResource(pid, ptype, rtype, rids);
    	}
    	for(String menuId:rids){
    		UmsAcl umsAcl = umsAclRepository.getAcl(pid, ptype, menuId, Resource.RESOURCE_MENU);
    		if(umsAcl!=null){
    			//先清空按钮权限
    			umsAcl.setAclState(0);
    			if(map!=null){
    				String[] buttonIds = map.get(menuId);
    				for(String buttonId:buttonIds){
    		    		UmsButton umsButton = umsButtonRepository.findOne(buttonId);
    		    		umsAcl.setPermission(umsButton.getButtonOrder(), true);
    		    	}
    			}
    		}else{
    			umsAcl = new UmsAcl();
    			umsAcl.setPid(pid);
    	    	umsAcl.setPtype(ptype);
    	    	umsAcl.setRid(menuId);
    	    	umsAcl.setRtype(Resource.RESOURCE_MENU);
    	    	//初始化按钮权限
    	    	umsAcl.setAclState(0);
    	    	if(map!=null){
    				String[] buttonIds = map.get(menuId);
    				for(String buttonId:buttonIds){
    		    		UmsButton umsButton = umsButtonRepository.findOne(buttonId);
    		    		umsAcl.setPermission(umsButton.getButtonOrder(), true);
    		    	}
    			}
    		}
    		umsAclRepository.save(umsAcl);
    	}
    }
    
    /**
     * 添加控制器资源和控制器操作权限
     * @param pid
     * @param ptype
     * @param rids
     * @param rtype
     * @param map
     */
    private void addPrincipalControllerAcl(String pid, String ptype, String[] rids, String rtype, Map<String,String[]> map){
    	//移除未分配的菜单
    	if(rids == null || rids.length == 0){
    		umsAclRepository.clearPrincipalResourceAcl(pid, ptype, rtype);
    	}else{
        	umsAclRepository.removeUnAssignedResource(pid, ptype, rtype, rids);
    	}
    	for(String controllerResId:rids){
    		UmsAcl umsAcl = umsAclRepository.getAcl(pid, ptype, controllerResId, Resource.RESOURCE_CONTROLLER);
    		if(umsAcl!=null){
    			//先清空按钮权限
    			umsAcl.setAclState(0);
    			if(map!=null){
    				String[] controllerOperIds = map.get(controllerResId);
    				for(String controllerOperId:controllerOperIds){
    		    		UmsControllerOper controllerOper = umsControllerOperRepository.findOne(controllerOperId);
    		    		umsAcl.setPermission(controllerOper.getIndexPos(), true);
    		    	}
    			}
    		}else{
    			umsAcl = new UmsAcl();
    			umsAcl.setPid(pid);
    	    	umsAcl.setPtype(ptype);
    	    	umsAcl.setRid(controllerResId);
    	    	umsAcl.setRtype(Resource.RESOURCE_CONTROLLER);
    	    	//初始化按钮权限
    	    	umsAcl.setAclState(0);
    	    	if(map!=null){
    				String[] controllerOperIds = map.get(controllerResId);
    				for(String controllerOperId:controllerOperIds){
    					UmsControllerOper controllerOper = umsControllerOperRepository.findOne(controllerOperId);
    		    		umsAcl.setPermission(controllerOper.getIndexPos(), true);
    		    	}
    			}
    		}
    		umsAclRepository.save(umsAcl);
    	}
    }
    
    /**
     * 根据主体id和主体类型获取权限信息
     * @param pid
     * @param ptype
     * @return
     */
	public List<UmsAcl> getAcl(String pid, String ptype) {
		return umsAclRepository.getAcl(pid, ptype);
	}
	
	/**
     * 根据主体id、主体类型、资源类型获取权限信息
     * @param pid
     * @param ptype
     * @param rtype
     * @return
     */
	public List<UmsAcl> getAcl(String pid, String ptype, String rtype) {
		return umsAclRepository.getAcl(pid, ptype, rtype);
	}

	/**
	 * 根据主体id、主体类型、资源id和资源类型获取权限信息
	 * @param pid
	 * @param ptype
	 * @param rid
	 * @param rtype
	 * @return
	 */
	public UmsAcl getAcl(String pid, String ptype,String rid,String rtype) {
		return umsAclRepository.getAcl(pid, ptype,rid,rtype);
	}

}
