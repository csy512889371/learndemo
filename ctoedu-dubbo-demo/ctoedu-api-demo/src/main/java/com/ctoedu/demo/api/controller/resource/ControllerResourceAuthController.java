package com.ctoedu.demo.api.controller.resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ListVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.resource.ControllerResourceForNavVO;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.facade.auth.service.UmsAuthFacade;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.service.UmsControllerResFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/menuResource/auth")
public class ControllerResourceAuthController {
	
	@Reference
	private UmsAuthFacade umsAuthFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsControllerResFacade umsControllerResFacade;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * get all authorized menu by conditions
	 * @return
	 */
	@RequestMapping(value="/findRequestUrls", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findMenu(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		Set<UmsControllerResources> umsControllerResources = null;
		List<UmsControllerResources> umsControllerResourceList = null;
		ListVO<ControllerResourceForNavVO> listVO = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername == null){
				umsControllerResources = new HashSet<>();
				listVO = new ListVO<>(umsControllerResources, ControllerResourceForNavVO.class);
			}else{
				String username = (String)currentUsername;
				String appSn = (String) request.getAttribute("currentAppSn");
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					Searchable searchable = Searchable.newSearchable();
					searchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
					searchable.addSearchParam("application.sn_eq", appSn);
					umsControllerResourceList = umsControllerResFacade.listUmsControllerRes(searchable);
					listVO = new ListVO<>(umsControllerResourceList, ControllerResourceForNavVO.class);
				}else{
					UmsUser user = umsUserFacade.findByUsername(username);
					umsControllerResources = umsControllerResFacade.getControllerResByUserAndAppSn(user, appSn, null, AvailableEnum.TRUE.getValue());
					listVO = new ListVO<>(umsControllerResources, ControllerResourceForNavVO.class);
				}
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
}
