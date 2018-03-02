package com.ctoedu.demo.api.controller.org;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.org.OrgForSelectVO;
import com.ctoedu.demo.api.controller.vo.org.OrgForTreeSelectVO;
import com.ctoedu.demo.api.controller.vo.org.OrgVO;
import com.ctoedu.demo.api.service.orgRole.OrgRoleService;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.api.service.user.UserService;
import com.ctoedu.demo.api.util.tree.FayTreeUtil;
import com.ctoedu.demo.api.util.tree.TreeUtil;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.org.service.UmsOrgFacade;

@Controller
@RequestMapping("/api/org")
public class OrgController {
	
	@Reference
	private UmsOrgFacade umsOrgFacade;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OrgRoleService orgRoleService;
	
	@RequestMapping(value="/findForPage", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findForPage(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		Page<UmsUserOrgRelation> uuors = null;
		Page<UmsOrg> pageOrg = null;
		PageVO<OrgVO> pageVO = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername != null){
				String username = (String)currentUsername;
				String name = obj.getString("name");
				int number = obj.getInteger("number");
				int size = obj.getInteger("size");
				Pageable page = new PageRequest(number, size);
				Searchable searchable = Searchable.newSearchable();
				searchable.setPage(page);
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					searchable.addSort(Direction.ASC, "orderNum");
					searchable.addSearchParam("name_like", name);
					pageOrg = umsOrgFacade.listUmsOrgPage(searchable);
					pageVO = new PageVO<>(pageOrg, OrgVO.class);
				}else if(orgRoleService.validate(username, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
					List<String> orgIds = userService.getOrgIdsForManageByLoginUser(username);
					searchable.addSort(Direction.ASC, "orderNum");
					searchable.addSearchParam("name_like", name);
					searchable.addSearchParam("id_in", orgIds);
					pageOrg = umsOrgFacade.listUmsOrgPage(searchable);
					pageVO = new PageVO<>(pageOrg, OrgVO.class);
				}else{
					searchable.addSort(Direction.ASC, "org.orderNum");
					searchable.addSearchParam("org.name_like", name);
					searchable.addSearchParam("user.username_eq", username);
					uuors = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
					pageVO = new PageVO<>(uuors, OrgVO.class);
				}
			}else{
				pageVO = new PageVO<>(new ArrayList<>(), OrgVO.class);
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
	
	@RequestMapping(value="/findInTree", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findInTree(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsUserOrgRelation> uuors = null;
		List<UmsOrg> listOrg = null;
		ListVO<OrgVO> listVO = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername != null){
				String username = (String)currentUsername;
				String name = obj.getString("name");
				Searchable searchable = Searchable.newSearchable();
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					searchable.addSort(Direction.ASC, "orderNum");
					searchable.addSearchParam("name_like", name);
					listOrg = umsOrgFacade.listUmsOrg(searchable);
					listVO = new ListVO<>(listOrg, OrgVO.class);
				}else if(orgRoleService.validate(username, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
					List<String> orgIds = userService.getOrgIdsForManageByLoginUser(username);
					searchable.addSort(Direction.ASC, "orderNum");
					searchable.addSearchParam("name_like", name);
					searchable.addSearchParam("id_in", orgIds);
					listOrg = umsOrgFacade.listUmsOrg(searchable);
					listVO = new ListVO<>(listOrg, OrgVO.class);
				}else{
					searchable.addSort(Direction.ASC, "org.orderNum");
					searchable.addSearchParam("org.name_like", name);
					searchable.addSearchParam("user.username_eq", username);
					uuors = umsOrgFacade.listUmsUserOrgRelation(searchable);
					listVO = new ListVO<>(uuors, OrgVO.class);
				}
			}else{
				listVO = new ListVO<>(new ArrayList<>(), OrgVO.class);
			}
			Object data = FayTreeUtil.getTreeInJsonObject(listVO.getVoList());
			
			result.setSuccess(true);
			result.setData(data);
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
		try {
			UmsOrg org = umsOrgFacade.getById(obj.getString("id"));
			OrgVO vo = new OrgVO();
			vo.convertPOToVO(org);
			result.setSuccess(true);
			result.setData(vo);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody ViewerResult add(@RequestBody UmsOrg org){
		ViewerResult result = new ViewerResult();
		try {
			if(org != null && org.getParent().getId() == null){
				org.setParent(null);
			}
			org.setManagerType((short)1);
			org = umsOrgFacade.createUmsOrg(org);
			OrgVO orgVO = new OrgVO();
			orgVO.convertPOToVO(org);
			result.setSuccess(true);
			result.setData(orgVO);
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
			umsOrgFacade.deleteUmsOrg(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody ViewerResult update(@RequestBody UmsOrg org){
		ViewerResult result = new ViewerResult();
		UmsOrg currentOrg = null;
		try {
			currentOrg = umsOrgFacade.getById(org.getId());
			currentOrg.setName(org.getName());
			currentOrg.setAddress(org.getAddress());
			currentOrg.setPhone(org.getPhone());
			currentOrg.setOrderNum(org.getOrderNum());
			if(org.getParent() != null && org.getParent().getId() != null){
				currentOrg.setParent(org.getParent());
			}
			org = umsOrgFacade.updateUmsOrg(currentOrg);
			OrgVO orgVO = new OrgVO();
			orgVO.convertPOToVO(org);
			result.setSuccess(true);
			result.setData(orgVO);
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
			short isAvailable = new Short(obj.getString("isAvailable"));
			UmsOrg org = umsOrgFacade.updAvailable(id, isAvailable);
			OrgVO orgVO = new OrgVO();
			orgVO.convertPOToVO(org);
			result.setSuccess(true);
			result.setData(orgVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/findForSelect", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findForSelect(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsOrg> orgList = null;
		List<UmsUserOrgRelation> userOrgRelationList = null;
		ListVO<OrgForSelectVO> listVO = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername != null){
				String username = (String)currentUsername;
				Searchable searchable = Searchable.newSearchable();
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					searchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
					searchable.addSort(Direction.ASC, "orderNum");
					orgList = umsOrgFacade.listUmsOrg(searchable);
					listVO = new ListVO<>(orgList, OrgForSelectVO.class);
				}else if(orgRoleService.validate(username, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
					List<String> orgIds = userService.getOrgIdsForManageByLoginUser(username);
					searchable.addSort(Direction.ASC, "orderNum");
					searchable.addSearchParam("id_in", orgIds);
					orgList = umsOrgFacade.listUmsOrg(searchable);
					listVO = new ListVO<>(orgList, OrgForSelectVO.class);
				}else{
					searchable.addSearchParam("org.isAvailable_eq", AvailableEnum.TRUE.getValue());
					searchable.addSort(Direction.ASC, "org.orderNum");
					searchable.addSearchParam("user.username_eq", username);
					userOrgRelationList = umsOrgFacade.listUmsUserOrgRelation(searchable);
					listVO = new ListVO<>(userOrgRelationList, OrgForSelectVO.class);
				}
			}else{
				listVO = new ListVO<>(new ArrayList<>(), OrgForSelectVO.class);
			}
			result.setSuccess(true);
			result.setData(listVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/findForTreeSelect", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findForTreeSelect(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsOrg> orgList = null;
		List<UmsUserOrgRelation> userOrgRelationList = null;
		ListVO<OrgForTreeSelectVO> listVO = null;
		Object data = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername != null){
				String username = (String)currentUsername;
				Searchable searchable = Searchable.newSearchable();
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN) || roleService.validate(username, FaySysRoleConstant.MANAGE_ROLE_SN)){
					searchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
					searchable.addSort(Direction.ASC, "orderNum");
					orgList = umsOrgFacade.listUmsOrg(searchable);
					listVO = new ListVO<>(orgList, OrgForTreeSelectVO.class);
				}else if(orgRoleService.validate(username, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
					List<String> orgIds = userService.getOrgIdsForManageByLoginUser(username);
					searchable.addSort(Direction.ASC, "orderNum");
					searchable.addSearchParam("id_in", orgIds);
					orgList = umsOrgFacade.listUmsOrg(searchable);
					listVO = new ListVO<>(orgList, OrgForTreeSelectVO.class);
				}else{
					searchable.addSearchParam("org.isAvailable_eq", AvailableEnum.TRUE.getValue());
					searchable.addSort(Direction.ASC, "org.orderNum");
					searchable.addSearchParam("user.username_eq", username);
					userOrgRelationList = umsOrgFacade.listUmsUserOrgRelation(searchable);
					listVO = new ListVO<>(userOrgRelationList, OrgForTreeSelectVO.class);
				}
			}else{
				listVO = new ListVO<>(new ArrayList<>(), OrgForTreeSelectVO.class);
			}
			data = TreeUtil.getTreeSelectInJsonObject(listVO.getVoList());
			result.setSuccess(true);
			result.setData(data);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}