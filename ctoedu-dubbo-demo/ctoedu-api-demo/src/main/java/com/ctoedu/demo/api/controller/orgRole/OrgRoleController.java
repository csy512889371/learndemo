package com.ctoedu.demo.api.controller.orgRole;

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
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ListVO;
import com.ctoedu.common.vo.PageVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.orgRole.OrgRoleVO;
import com.ctoedu.demo.api.service.orgRole.OrgRoleService;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.api.service.user.UserService;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.service.UmsOrgFacade;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.service.UmsOrgRoleFacade;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/orgRole")
public class OrgRoleController {
	
	@Reference
	private UmsOrgFacade umsOrgFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OrgRoleService orgRoleService;
	
	@Autowired
	private UserService userService;
	
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
		Page<UmsOrgRole> pageRole = null;
		Page<UmsUserOrgRoleRelation> uuorrs = null;
		PageVO<OrgRoleVO> pageVO = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername != null){
				String username = (String)currentUsername;
				String name = obj.getString("name");
				String orgId = obj.getString("orgId");
				int number = obj.getInteger("number");
				int size = obj.getInteger("size");
				Pageable page = new PageRequest(number, size);
				Searchable searchable = Searchable.newSearchable();
				searchable.setPage(page);
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					searchable.addSearchParam("name_like", name);
					searchable.addSearchParam("orgId_eq", orgId);
					pageRole = umsOrgRoleFacade.listPage(searchable);
					pageVO = new PageVO<>(pageRole, OrgRoleVO.class);
				}else if(orgRoleService.validate(username, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
					List<String> orgIds = userService.getOrgIdsForManageByLoginUser(username);
					searchable.addSearchParam("name_like", name);
					if(orgId == null){
						searchable.addSearchParam("orgId_in", orgIds);
						pageRole = umsOrgRoleFacade.listPage(searchable);
						pageVO = new PageVO<>(pageRole, OrgRoleVO.class);
					}else if(orgIds.contains(orgId)){
						searchable.addSearchParam("orgId_eq", orgId);
						pageRole = umsOrgRoleFacade.listPage(searchable);
						pageVO = new PageVO<>(pageRole, OrgRoleVO.class);
					}else{
						pageVO = new PageVO<>(new ArrayList<>(), OrgRoleVO.class);
					}
				}else{
					searchable.addSearchParam("orgId_eq", orgId);
					searchable.addSearchParam("role.name_like", name);
					searchable.addSearchParam("user.username_eq", username);
					uuorrs = umsOrgRoleFacade.listUmsUserRoleRelationPage(searchable);
					pageVO = new PageVO<>(uuorrs, OrgRoleVO.class);
				}
				for(OrgRoleVO vo:pageVO.getPageData()){
					String oId = vo.getOrgId();
					if(oId != null){
						UmsOrg org = umsOrgFacade.getById(oId);
						if(org != null) vo.setOrgName(org.getName());
					}
				}
			}else{
				pageVO = new PageVO<>(new ArrayList<>(), OrgRoleVO.class);
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
		UmsOrgRole role = null;
		try {
			String id = obj.getString("id");
			role = umsOrgRoleFacade.getById(id);
			OrgRoleVO roleVO = new OrgRoleVO();
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
	public @ResponseBody ViewerResult add(@RequestBody UmsOrgRole role){
		ViewerResult result = new ViewerResult();
		try {
			String sn = role.getSn();
			for(String d : FayOrgRoleConstant.DEFAULT_ROLE_SN_PREFIX){
				if(sn.startsWith(d)){
					result.setSuccess(false);
					result.setErrMessage(d+"是系统的内置前缀，不可使用");
					return result;
				}
			}
			role = umsOrgRoleFacade.create(role);
			OrgRoleVO roleVO = new OrgRoleVO();
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
				UmsOrgRole role = umsOrgRoleFacade.getById(id);
				String sn = role.getSn();
				for(String d : FayOrgRoleConstant.DEFAULT_ROLE_SN_PREFIX){
					if(sn.startsWith(d)){
						result.setSuccess(false);
						result.setErrMessage("此角色是系统内置角色，不可删除");
						return result;
					}
				}
			}
			umsOrgRoleFacade.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody ViewerResult update(@RequestBody UmsOrgRole role){
		ViewerResult result = new ViewerResult();
		UmsOrgRole currentRole = null;
		try {
			currentRole = umsOrgRoleFacade.getById(role.getId());
			String sn = currentRole.getSn();
			for(String d : FayOrgRoleConstant.DEFAULT_ROLE_SN_PREFIX){
				if(sn.startsWith(d)){
					result.setSuccess(false);
					result.setErrMessage("此角色是系统的内置角色，不可更新");
					return result;
				}
			}
			currentRole.setName(role.getName());
			currentRole.setSn(role.getSn());
			currentRole.setOrgId(role.getOrgId());
			role = umsOrgRoleFacade.update(currentRole);
			OrgRoleVO roleVO = new OrgRoleVO();
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
			UmsOrgRole currentRole = umsOrgRoleFacade.getById(id);
			String sn = currentRole.getSn();
			for(String d : FayOrgRoleConstant.DEFAULT_ROLE_SN_PREFIX){
				if(sn.startsWith(d)){
					result.setSuccess(false);
					result.setErrMessage("此角色是系统的内置角色，不可更改");
					return result;
				}
			}
			short isAvailable = new Short(obj.getString("isAvailable"));
			UmsOrgRole role = umsOrgRoleFacade.updAvailable(id, isAvailable);
			OrgRoleVO roleVO = new OrgRoleVO();
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
	
	@RequestMapping(value="/findByOrg", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findByApp(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsOrgRole> roles = null;
		try {
			String orgId = obj.getString("orgId");
			Searchable searchable = Searchable.newSearchable();
			searchable.addSearchParam("orgId_eq", orgId);
			roles = umsOrgRoleFacade.list(searchable);
			ListVO<OrgRoleVO> vos = new ListVO<>(roles, OrgRoleVO.class);
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
