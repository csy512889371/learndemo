package com.ctoedu.demo.api.controller.person;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.PageVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.person.PersonVO;
import com.ctoedu.demo.api.controller.vo.user.UserVO;
import com.ctoedu.demo.api.service.user.DepartmentUserService;
import com.ctoedu.demo.api.service.user.OrgUserService;
import com.ctoedu.demo.api.service.user.PositionUserService;
import com.ctoedu.demo.api.service.user.UserService;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.service.UmsRoleFacade;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsPersonFacade;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

/**
 * person api
 * @author feichongzheng
 *
 */
@Controller
@RequestMapping("/api/person")
public class PersonController {
	
	@Reference
	private UmsPersonFacade umsPersonFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsRoleFacade umsRoleFacade;
	
	@Autowired
	private UserService userService;

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private PositionUserService positionUserService;

	@Autowired
	private DepartmentUserService departmentUserService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/findForPage", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findForPage(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		PageVO<PersonVO> pageVO = null;
//		PageVO<RoleUserVO> pageVOForRole = null;
		List<String> currentUserRoleSn = null;
		try {
			Object currentUsernameObj = request.getAttribute("currentUsername");
			Object object = request.getAttribute("currentUserRoleSn");
			if(object == null){
				currentUserRoleSn = new ArrayList<>();
			}else{
				currentUserRoleSn = (List<String>) request.getAttribute("currentUserRoleSn");
			}
			if(currentUsernameObj == null){
				pageVO = new PageVO<>(new ArrayList<>(), PersonVO.class);
				result.setData(pageVO);
			}else{
				String currentUsername = (String)currentUsernameObj;
				
				String orgId = obj.getString("orgId");
				String departmentId = obj.getString("departmentId");
				String positionId = obj.getString("positionId");
//				String roleId = obj.getString("roleId");
				String name = obj.getString("name");
				String sn = obj.getString("sn");
				
				int number = obj.getInteger("number");
				int size = obj.getInteger("size");
				
				if(positionId == null){
					if(departmentId == null){
						if(orgId == null){
//							if(roleId == null){
								pageVO = userService.getPersonByLoginUser(name, sn, number, size, currentUsername, currentUserRoleSn);
								result.setData(pageVO);
//							}else{
//								pageVOForRole = roleUserService.getUser(nickname, username, roleId, number, size, currentUsername, currentUserRoleSn);
//								result.setData(pageVOForRole);
//							}
						}else{
//							if(roleId == null){
								pageVO = orgUserService.getPerson(name, sn, orgId, number, size, currentUsername, currentUserRoleSn);
								result.setData(pageVO);
//							}else{
//								pageVOForRole = orgRoleUserService.getUser(nickname, username, orgId, roleId, number, size, currentUsername, currentUserRoleSn);
//								result.setData(pageVOForRole);
//							}
						}
					}else{
//						if(roleId == null){
							pageVO = departmentUserService.getPerson(name, sn, departmentId, number, size, currentUsername, currentUserRoleSn);
							result.setData(pageVO);
//						}else{
//							pageVOForRole = departmentRoleUserService.getUser(nickname, username, departmentId, roleId, number, size, currentUsername, currentUserRoleSn);
//							result.setData(pageVOForRole);
//						}
					}
				}else{
//					if(roleId == null){
						pageVO = positionUserService.getPerson(name, sn, positionId, number, size, currentUsername, currentUserRoleSn);
						result.setData(pageVO);
//					}else{
//						pageVOForRole = positionRoleUserService.getUser(nickname, username, positionId, roleId, number, size, currentUsername, currentUserRoleSn);
//						result.setData(pageVOForRole);
//					}
				}
			}
			result.setSuccess(true);
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
		UmsPerson person = null;
		try {
			String id = obj.getString("id");
			person = umsPersonFacade.findById(id);
			PersonVO personVO = new PersonVO();
			personVO.convertPOToVO(person);
			result.setSuccess(true);
			result.setData(personVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody ViewerResult add(@RequestBody UmsPerson person){
		ViewerResult result = new ViewerResult();
		try {
			UmsUser user = person.getUser();
			if(user == null) user = new UmsUser();
			if(user.getNickname() == null) user.setNickname(person.getName());
			if(user.getUsername() == null) user.setUsername(person.getSn());
			if(user.getPassword() == null) user.setPassword(FayUserConstant.DEFAULT_PASSWORD);
			user.setPerson(person);
			user = umsUserFacade.register(user);
			
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
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody ViewerResult update(@RequestBody UmsUser user){
		ViewerResult result = new ViewerResult();
		UmsUser oldUser = null;
		try {
			oldUser = umsUserFacade.findById(user.getId());
			UmsPerson person = oldUser.getPerson();
			person.setName(user.getPerson().getName());
			person = umsPersonFacade.updatePerson(person);
			oldUser.setPerson(person);
			UserVO userVO = new UserVO();
			userVO.convertPOToVO(oldUser);
			result.setSuccess(true);
			result.setData(userVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
