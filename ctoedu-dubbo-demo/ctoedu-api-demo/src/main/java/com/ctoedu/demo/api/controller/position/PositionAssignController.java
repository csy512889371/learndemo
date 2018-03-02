package com.ctoedu.demo.api.controller.position;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.vo.PageVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.controller.vo.user.AssignUserVO;
import com.ctoedu.demo.facade.position.service.UmsPositionFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;

@Controller
@RequestMapping("/api/position")
public class PositionAssignController {
	
	@Reference
	private UmsPositionFacade umsPositionFacade;
	
	/**
	 * get all assigned users by conditions
	 * @return
	 */
	@RequestMapping(value="/findAssignedUsers", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findUsersForAssign(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		Page<UmsUser> pageUser = null;
		PageVO<AssignUserVO> pageVO = null;
		try {
			//get JSON format parameters
			String positionId = obj.getString("dataId");
			String name = obj.getString("name");
			String sn = obj.getString("sn");
			int number = Integer.valueOf(obj.getString("number"));
			int size = Integer.valueOf(obj.getString("size"));
			
//			Sort sort = new Sort(Direction.DESC, "createDate");
			
			Pageable page = new PageRequest(number, size);
			
			pageUser = umsPositionFacade.findUserByPositionIdAndNameAndSn(positionId, name, sn, page);
			//convert to PageVO for view
			pageVO = new PageVO<>(pageUser, AssignUserVO.class);
			result.setSuccess(true);
			result.setData(pageVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * get all unassigned users by conditions
	 * @return
	 */
	@RequestMapping(value="/findUnassignedUsers", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findUnassignedUsers(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		Page<UmsUser> pageUser = null;
		PageVO<AssignUserVO> pageVO = null;
		try {
			//get JSON format parameters
			String positionId = obj.getString("dataId");
			String name = obj.getString("name");
			String sn = obj.getString("sn");
			int number = Integer.valueOf(obj.getString("number"));
			int size = Integer.valueOf(obj.getString("size"));
			
//			Sort sort = new Sort(Direction.DESC, "createDate");
			
			Pageable page = new PageRequest(number, size);
			
			pageUser = umsPositionFacade.findNotUserByPositionIdAndNameAndSn(positionId, name, sn, page);
			//convert to PageVO for view
			pageVO = new PageVO<>(pageUser, AssignUserVO.class);
			result.setSuccess(true);
			result.setData(pageVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/assignUser", method=RequestMethod.POST)
	public @ResponseBody ViewerResult assignUser(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		try {
			//get JSON format parameters
			String positionId = obj.getString("dataId");
			List<String> userIds = (List<String>) obj.get("userIds");
			umsPositionFacade.buildUserPositionRelation(positionId, userIds);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("分配用户失败");
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/unassignUser", method=RequestMethod.POST)
	public @ResponseBody ViewerResult unassignUser(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		try {
			//get JSON format parameters
			String positionId = obj.getString("dataId");
			List<String> userIds = (List<String>) obj.get("userIds");
			umsPositionFacade.clearUserPositionRelation(positionId, userIds);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("去掉分配用户失败");
			e.printStackTrace();
		}
		return result;
	}
}
