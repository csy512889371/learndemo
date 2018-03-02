package com.ctoedu.demo.api.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.controller.vo.user.UserVO;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
import com.ctoedu.demo.facade.department.service.UmsDepartmentFacade;
import com.ctoedu.demo.facade.org.service.UmsOrgFacade;
import com.ctoedu.demo.facade.orgRole.service.UmsOrgRoleFacade;
import com.ctoedu.demo.facade.position.service.UmsPositionFacade;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.service.UmsRoleFacade;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

/**
 * user api
 * @author feichongzheng
 *
 */
@Controller
@RequestMapping("/api/user/relation")
public class UserRelationController {
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsRoleFacade umsRoleFacade;
	
	@Reference
	private UmsOrgFacade umsOrgFacade;

	@Reference
	private UmsDepartmentFacade umsDepartmentFacade;

	@Reference
	private UmsPositionFacade umsPositionFacade;

	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody ViewerResult add(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		UmsUser user = new UmsUser();
		UmsPerson person = new UmsPerson();
		try {
			String name = obj.getString("name");
			String sn = obj.getString("sn");
			String password = obj.getString("password");
			user.setNickname(name);
			user.setUsername(sn);
			user.setPassword(password);
			person.setName(name);
			person.setSn(sn);
			user.setPerson(person);
			user = umsUserFacade.register(user);
			
			String orgId = obj.getString("orgId");
			String departmentId = obj.getString("departmentId");
			String positionId = obj.getString("positionId");
			String roleId = obj.getString("roleId");
			String orgRoleId = obj.getString("orgRoleId");
			List<String> userIds = new ArrayList<>();
			userIds.add(user.getId());
			
			if(orgId != null){
				umsOrgFacade.buildUserOrgRelation(orgId, userIds);
			}
			if(departmentId != null){
				umsDepartmentFacade.buildUserDepartmentRelation(departmentId, userIds);
			}
			if(positionId != null){
				umsPositionFacade.buildUserPositionRelation(positionId, userIds);
			}
			if(roleId != null){
				umsRoleFacade.buildUserRoleRelation(roleId, userIds.toArray(new String[userIds.size()]));
			}
			if(orgRoleId != null){
				umsOrgRoleFacade.buildUserRoleRelation(orgRoleId, userIds.toArray(new String[userIds.size()]));
			}
			
			Searchable searchable = Searchable.newSearchable();
			List<UmsApp> apps = umsAppFacade.list(searchable);
			for(UmsApp app : apps){
				UmsRole role = umsRoleFacade.getBySn(FaySysRoleConstant.GENERAL_ROLE_SN_PREFIX+app.getSn());
				if(role != null){
					umsRoleFacade.buildUserRoleRelation(role.getId(), new String[]{user.getId()});
				}
			}
			
			UserVO userVO = new UserVO();
			userVO.convertPOToVO(user);
			result.setSuccess(true);
			result.setData(userVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public @ResponseBody ViewerResult remove(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		try {
			String userId = obj.getString("userId");
			String orgId = obj.getString("orgId");
			String departmentId = obj.getString("departmentId");
			String positionId = obj.getString("positionId");
			String roleId = obj.getString("roleId");
			List<String> userIds = new ArrayList<>();
			userIds.add(userId);
			
			if(orgId != null){
				umsOrgFacade.clearUserOrgRelation(orgId, userIds);
			}
			if(departmentId != null){
				umsDepartmentFacade.clearUserDepartmentRelation(departmentId, userIds);
			}
			if(positionId != null){
				umsPositionFacade.clearUserPositionRelation(positionId, userIds);
			}
			if(roleId != null){
				umsRoleFacade.clearUserRoleRelation(roleId, userIds);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
