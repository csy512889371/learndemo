package com.ctoedu.demo.api.controller.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ListVO;
import com.ctoedu.common.vo.PageVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.role.RoleVO;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.facade.role.service.UmsRoleFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/role")
public class RoleController {
	
	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsRoleFacade umsRoleFacade;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * get all roles by conditions for page
	 * @param name
	 * @param number
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/findForPage", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findForPage(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		Page<UmsRole> pageRole = null;
		Page<UmsUserRoleRelation> uurrs = null;
		PageVO<RoleVO> pageVO = null;
		List<UmsApp> appList = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername != null){
				String username = (String)currentUsername;
				String name = obj.getString("name");
				String appId = obj.getString("appId");
				int number = obj.getInteger("number");
				int size = obj.getInteger("size");
				Pageable page = new PageRequest(number, size);
				Searchable searchable = Searchable.newSearchable();
				searchable.setPage(page);
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					searchable.addSearchParam("name_like", name);
					searchable.addSearchParam("appId_eq", appId);
					pageRole = umsRoleFacade.listPage(searchable);
					pageVO = new PageVO<>(pageRole, RoleVO.class);
				}else{
					UmsUser user = umsUserFacade.findByUsername((String)currentUsername);
					appList = umsAppFacade.findAppByUserRoleRelation(user.getId(), AvailableEnum.TRUE.getValue(), FaySysRoleConstant.MANAGE_ROLE_SN);
					List<String> appIds = new ArrayList<>();
					boolean flag = true;
					for(UmsApp app : appList){
						appIds.add(app.getId());
						if(app.getId().equals(appId)){
							flag = false;
							searchable.addSearchParam("application.id_eq", appId);
							break;
						}
					}
					if(flag)
					searchable.addSearchParam("role.appId_in", appIds);
					
					searchable.addSearchParam("role.name_like", name);
					searchable.addSearchParam("user.username_eq", username);
					uurrs = umsRoleFacade.listUmsUserRoleRelationPage(searchable);
					pageVO = new PageVO<>(uurrs, RoleVO.class);
				}
				for(RoleVO vo:pageVO.getPageData()){
					String aId = vo.getAppId();
					if(aId != null){
						UmsApp app = umsAppFacade.getById(aId);
						if(app != null) vo.setAppName(app.getName());
					}
				}
			}else{
				pageVO = new PageVO<>(new ArrayList<>(), RoleVO.class);
			}
			result.setSuccess(true);
			result.setData(pageVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/findById", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findById(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		UmsRole role = null;
		try {
			String id = obj.getString("id");
			role = umsRoleFacade.getById(id);
			RoleVO roleVO = new RoleVO();
			roleVO.convertPOToVO(role);
			result.setSuccess(true);
			result.setData(roleVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody ViewerResult add(@RequestBody UmsRole role){
		ViewerResult result = new ViewerResult();
		try {
			String sn = role.getSn();
			for(String d : FaySysRoleConstant.DEFAULT_ROLE_SN_PREFIX){
				if(sn.startsWith(d)){
					result.setSuccess(false);
					result.setErrMessage(d+"是系统的内置前缀，不可使用");
					return result;
				}
			}
			role = umsRoleFacade.create(role);
			RoleVO roleVO = new RoleVO();
			roleVO.convertPOToVO(role);
			result.setSuccess(true);
			result.setData(roleVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody ViewerResult delete(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		try {
			JSONArray ja = obj.getJSONArray("ids");
			String[] ids = ja.toJavaObject(String[].class);
			for(String id : ids){
				UmsRole role = umsRoleFacade.getById(id);
				String sn = role.getSn();
				for(String d : FaySysRoleConstant.DEFAULT_ROLE_SN_PREFIX){
					if(sn.startsWith(d)){
						result.setSuccess(false);
						result.setErrMessage("此角色是系统内置角色，不可删除");
						return result;
					}
				}
			}
			umsRoleFacade.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody ViewerResult update(@RequestBody UmsRole role){
		ViewerResult result = new ViewerResult();
		UmsRole currentRole = null;
		try {
			currentRole = umsRoleFacade.getById(role.getId());
			String sn = currentRole.getSn();
			for(String d : FaySysRoleConstant.DEFAULT_ROLE_SN_PREFIX){
				if(sn.startsWith(d)){
					result.setSuccess(false);
					result.setErrMessage("此角色是系统的内置角色，不可更新");
					return result;
				}
			}
			currentRole.setName(role.getName());
			currentRole.setSn(role.getSn());
			currentRole.setAppId(role.getAppId());
			role = umsRoleFacade.update(currentRole);
			RoleVO roleVO = new RoleVO();
			roleVO.convertPOToVO(role);
			result.setSuccess(true);
			result.setData(roleVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/updAvailable", method=RequestMethod.POST)
	public @ResponseBody ViewerResult updAvailable(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		try {
			String id = obj.getString("id");
			UmsRole currentRole = umsRoleFacade.getById(id);
			String sn = currentRole.getSn();
			for(String d : FaySysRoleConstant.DEFAULT_ROLE_SN_PREFIX){
				if(sn.startsWith(d)){
					result.setSuccess(false);
					result.setErrMessage("此角色是系统的内置角色，不可更改");
					return result;
				}
			}
			short isAvailable = new Short(obj.getString("isAvailable"));
			UmsRole role = umsRoleFacade.updAvailable(id, isAvailable);
			RoleVO roleVO = new RoleVO();
			roleVO.convertPOToVO(role);
			result.setSuccess(true);
			result.setData(roleVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/findByApp", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findByApp(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsRole> roles = null;
		try {
			String appId = obj.getString("appId");
			Searchable searchable = Searchable.newSearchable();
			searchable.addSearchParam("appId_eq", appId);
			roles = umsRoleFacade.list(searchable);
			ListVO<RoleVO> vos = new ListVO<>(roles, RoleVO.class);
			result.setSuccess(true);
			result.setData(vos);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
