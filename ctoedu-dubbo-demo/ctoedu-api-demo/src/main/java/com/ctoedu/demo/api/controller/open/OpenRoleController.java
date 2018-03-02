package com.ctoedu.demo.api.controller.open;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ListVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.controller.vo.open.role.OrgRoleVO;
import com.ctoedu.demo.api.controller.vo.open.role.RoleVO;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
import com.ctoedu.demo.facade.auth.service.UmsAuthFacade;
import com.ctoedu.demo.facade.jwt.domain.Payload;
import com.ctoedu.demo.facade.jwt.service.UmsJwtFacade;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.service.UmsOrgRoleFacade;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.facade.role.service.UmsRoleFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/open/role")
public class OpenRoleController {
	
	@Reference
	private UmsUserFacade umsUserFacade;

	@Reference
	private UmsRoleFacade umsRoleFacade;
	
	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;

	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsJwtFacade umsJwtFacade;

	@Reference
	private UmsAuthFacade umsAuthFacade;

	@RequestMapping(value="/findSysRoles", method=RequestMethod.GET)
	public @ResponseBody ViewerResult findSysRoles(HttpServletRequest request, HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		try {
			JSONObject obj = JSONObject.parseObject(request.getHeader("Authorization"));
			if(obj == null || obj.getString("appSn") == null){
				request.getRequestDispatcher("/api/auth/pass/badRequest").forward(request, response);
			}else{
				String appSn = obj.getString("appSn");
				String jwt = obj.getString("token");
				if(jwt != null){
					Payload payload = umsJwtFacade.getPayload(jwt);
					if(payload == null){
						request.getRequestDispatcher("/api/auth/pass/loginExpired").forward(request, response);
					}else if(payload.getExp() > (new Date()).getTime()){
						String username = payload.getName();
						UmsUser user = umsUserFacade.findByUsername(username);
						if(user.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
							UmsApp app = umsAppFacade.getBySn(appSn);
							Searchable searchable = Searchable.newSearchable();
							searchable.addSearchParam("user.username_eq", username);
							searchable.addSearchParam("role.appId_eq", app.getId());
							searchable.addSearchParam("role.isAvailable_eq", AvailableEnum.TRUE.getValue());
							List<UmsUserRoleRelation> uurrs = umsRoleFacade.listUmsUserRoleRelation(searchable);
							ListVO<RoleVO> list = new ListVO<>(uurrs, RoleVO.class);
							result.setData(list);
							result.setSuccess(true);
						}else{
							request.getRequestDispatcher("/api/auth/pass/userIsNotAvailable").forward(request, response);
						}
					}else{
						request.getRequestDispatcher("/api/auth/pass/loginExpired").forward(request, response);
					}
				}else{
					request.getRequestDispatcher("/api/auth/pass/noLogin").forward(request, response);
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("获取系统角色信息失败");
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/findOrgRoles", method=RequestMethod.GET)
	public @ResponseBody ViewerResult findOrgRoles(HttpServletRequest request, HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		try {
			JSONObject obj = JSONObject.parseObject(request.getHeader("Authorization"));
			if(obj == null || obj.getString("appSn") == null){
				request.getRequestDispatcher("/api/auth/pass/badRequest").forward(request, response);
			}else{
				String appSn = obj.getString("appSn");
				String jwt = obj.getString("token");
				if(jwt != null){
					Payload payload = umsJwtFacade.getPayload(jwt);
					if(payload == null){
						request.getRequestDispatcher("/api/auth/pass/loginExpired").forward(request, response);
					}else if(payload.getExp() > (new Date()).getTime()){
						String username = payload.getName();
						UmsUser user = umsUserFacade.findByUsername(username);
						if(user.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
							UmsApp app = umsAppFacade.getBySn(appSn);
							Searchable searchable = Searchable.newSearchable();
							searchable.addSearchParam("user.username_eq", username);
							searchable.addSearchParam("role.appId_eq", app.getId());
							searchable.addSearchParam("role.isAvailable_eq", AvailableEnum.TRUE.getValue());
							List<UmsUserOrgRoleRelation> uuorrs = umsOrgRoleFacade.listUmsUserRoleRelation(searchable);
							ListVO<OrgRoleVO> list = new ListVO<>(uuorrs, OrgRoleVO.class);
							result.setData(list);
							result.setSuccess(true);
						}else{
							request.getRequestDispatcher("/api/auth/pass/userIsNotAvailable").forward(request, response);
						}
					}else{
						request.getRequestDispatcher("/api/auth/pass/loginExpired").forward(request, response);
					}
				}else{
					request.getRequestDispatcher("/api/auth/pass/noLogin").forward(request, response);
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("获取机构角色信息失败");
			e.printStackTrace();
		}
		return result;
	}
}