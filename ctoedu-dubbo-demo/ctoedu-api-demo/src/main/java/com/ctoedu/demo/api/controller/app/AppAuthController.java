package com.ctoedu.demo.api.controller.app;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ctoedu.demo.api.controller.vo.common.AppForSelectVO;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;
import com.ctoedu.demo.facade.resource.service.UmsControllerResFacade;
import com.ctoedu.demo.facade.resource.service.UmsMenuResFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/app/auth")
public class AppAuthController {
	
	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;

	@Reference
	private UmsMenuResFacade umsMenuResFacade;

	@Reference
	private UmsControllerResFacade umsControllerResFacade;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * get all app by conditions for select
	 * @return
	 */
	@RequestMapping(value="/findAppForSelect", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findAppForSelect(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsApp> appList = null;
		ListVO<AppForSelectVO> listVO = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername == null){
				appList = new ArrayList<UmsApp>();
			}else{
				String username = (String)currentUsername;
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					Searchable searchable = Searchable.newSearchable();
					appList = umsAppFacade.list(searchable);
				}else{
					UmsUser user = umsUserFacade.findByUsername((String)currentUsername);
					appList = umsAppFacade.findAppByUserRoleRelation(user.getId(), AvailableEnum.TRUE.getValue(), FaySysRoleConstant.MANAGE_ROLE_SN_PREFIX);
				}
			}
			listVO = new ListVO<>(appList, AppForSelectVO.class);
			result.setSuccess(true);
			result.setData(listVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/findAppForAuthSelect", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findAppForAuthSelect(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsApp> appList = null;
		ListVO<AppForSelectVO> listVO = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername == null){
				appList = new ArrayList<UmsApp>();
				listVO = new ListVO<>(appList, AppForSelectVO.class);
			}else{
				String username = (String)currentUsername;
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					Searchable searchable = Searchable.newSearchable();
					appList = umsAppFacade.list(searchable);
					listVO = new ListVO<>(appList, AppForSelectVO.class);
				}else{
					UmsUser user = umsUserFacade.findByUsername(username);
					Searchable searchable = Searchable.newSearchable();
					appList = umsAppFacade.list(searchable);
					List<UmsApp> needApps = new ArrayList<>();
					for(UmsApp app : appList){
						String appSn = app.getSn();
						Set<UmsMenuResources> menuSet= umsMenuResFacade.getMenusByUserAndAppSn(user, appSn, AvailableEnum.TRUE.getValue());
						if(menuSet.isEmpty()){
							Set<UmsControllerResources> controllerSet = umsControllerResFacade.getControllerResByUserAndAppSn(user, appSn, null, AvailableEnum.TRUE.getValue());
							if(!controllerSet.isEmpty()){
								needApps.add(app);
							}
						}else{
							needApps.add(app);
						}
					}
					listVO = new ListVO<>(needApps, AppForSelectVO.class);
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
