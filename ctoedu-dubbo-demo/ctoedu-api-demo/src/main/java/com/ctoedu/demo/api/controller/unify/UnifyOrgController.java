package com.ctoedu.demo.api.controller.unify;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.vo.ListVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.controller.vo.unify.OrgTreeVO;
import com.ctoedu.demo.api.service.orgDepartmentPositionUser.OrgDepartmentPositionUserService;
import com.ctoedu.demo.api.util.tree.FayTreeUtil;

@Controller
@RequestMapping("/api/unify")
public class UnifyOrgController {
	
	@Autowired
	private OrgDepartmentPositionUserService orgDepartmentPositionUserService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/findOrgInTree", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findInTree(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		ListVO<OrgTreeVO> listVO = null;
		Object data = null;
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername != null){
				String username = (String)currentUsername;
				List<String> currentUserRoleSn = (List<String>) request.getAttribute("currentUserRoleSn");
				String name = obj.getString("name");
				data = orgDepartmentPositionUserService.findOrgDepartmentPositionUserInTreeByLoginUser(name, username, currentUserRoleSn);
			}else{
				listVO = new ListVO<>(new ArrayList<>(), OrgTreeVO.class);
				data = FayTreeUtil.getTreeInJsonObject(listVO.getVoList());
			}
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