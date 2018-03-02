package com.ctoedu.demo.api.controller.log;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.utils.DateUtil;
import com.ctoedu.common.utils.NetworkUtil;
import com.ctoedu.common.vo.PageVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.controller.vo.log.LogVO;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
import com.ctoedu.demo.facade.jwt.service.UmsJwtFacade;
import com.ctoedu.demo.facade.log.entity.UmsLog;
import com.ctoedu.demo.facade.log.enums.LogLevelEnum;
import com.ctoedu.demo.facade.log.enums.LogTypeEnum;
import com.ctoedu.demo.facade.log.enums.OpResultEnum;
import com.ctoedu.demo.facade.log.service.UmsLogFacade;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;
import com.ctoedu.demo.facade.resource.service.UmsControllerResFacade;
import com.ctoedu.demo.facade.resource.service.UmsMenuResFacade;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/log")
public class LogController {
	
	@Reference
	private UmsLogFacade umsLogFacade;
	
	@Reference
	private UmsJwtFacade umsJwtFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsMenuResFacade umsMenuResFacade;
	
	@Reference
	private UmsControllerResFacade umsControllerResFacade;
	
	@RequestMapping(value="/findForPage", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findForPage(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		Page<UmsLog> page = null;
		PageVO<LogVO> pageVO = null;
		try {
			String username = obj.getString("username");
			JSONArray rangeDate = obj.getJSONArray("rangeDate");
			LocalDateTime startOpTime = DateUtil.utilDateToLocalDateTime(rangeDate.getDate(0));
			LocalDateTime endOpTime = DateUtil.utilDateToLocalDateTime(rangeDate.getDate(1));
			int number = obj.getInteger("number");
			int size = obj.getInteger("size");
			Pageable pageable = new PageRequest(number, size);
			Searchable searchable = Searchable.newSearchable();
			searchable.setPage(pageable);
			searchable.addSearchParam("username_like", username);
			searchable.addSearchParam("opTime_gte", startOpTime);
			searchable.addSearchParam("opTime_lte", endOpTime);
			searchable.addSort(Direction.DESC, "opTime");
			page = umsLogFacade.listPage(searchable);
			pageVO = new PageVO<>(page, LogVO.class);
			for(LogVO vo : pageVO.getPageData()){
				String sn = vo.getApp();
				if(sn != null) {
					UmsApp app = umsAppFacade.getBySn(sn);
					if(app != null){
						vo.setApp(app.getName());
					}
				}
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
	
	@RequestMapping(value="/saveAction", method=RequestMethod.POST)
	public @ResponseBody ViewerResult request(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj){
		return log(request, response, obj, LogTypeEnum.ACTION.getValue());
	}
	
	@RequestMapping(value="/saveInterface", method=RequestMethod.POST)
	public @ResponseBody ViewerResult response(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj){
		return log(request, response, obj, LogTypeEnum.INTERFACE.getValue());
	}
	
	private ViewerResult log(HttpServletRequest request, HttpServletResponse response, JSONObject obj, Short logType){
		ViewerResult result = new ViewerResult();
		try {
			JSONObject authorization = JSONObject.parseObject(request.getHeader("Authorization"));
			if(authorization == null || authorization.getString("appSn") == null){
				result.setSuccess(false);
				result.setErrMessage("非法请求");
			}else{
				String menuPath = obj.getString("menuPath");
				String frontEndAccessPath = obj.getString("accessPath");
				String opDesc = obj.getString("opDesc");
				Long execTime = obj.getLong("execTime");
				int status = obj.getInteger("status");
				long currentDateTime = (new Date()).getTime();
				long mOpTime = currentDateTime-execTime;
				LocalDateTime opTime = DateUtil.utilDateToLocalDateTime(new Date(mOpTime));
				Object appSnO = request.getAttribute("currentAppSn");
				Object usernameO = request.getAttribute("currentUsername");
				String appSn = appSnO == null ? null : (String)appSnO;
				String username = usernameO == null ? null : (String)usernameO;
				UmsLog umsLog = new UmsLog();
				Searchable searchable = Searchable.newSearchable();
				if(frontEndAccessPath == null){
					searchable.addSearchParam("controllerUrlMapping_eq", frontEndAccessPath);
					searchable.addSearchParam("menu.menuUrl_eq", menuPath);
					searchable.addSearchParam("application.sn_eq", appSn);
					searchable.addSearchParam("menu.application.sn_eq", appSn);
					List<UmsMenuResources> menus = umsMenuResFacade.listUmsMenuResources(searchable);
					if(menus.size()>0){
						UmsMenuResources menu = menus.get(0);
						umsLog.setOpResource(menu.getMenuName());
					}
				}else{
					searchable.addSearchParam("controllerUrlMapping_eq", frontEndAccessPath);
					searchable.addSearchParam("menu.menuUrl_eq", menuPath);
					searchable.addSearchParam("application.sn_eq", appSn);
					searchable.addSearchParam("menu.application.sn_eq", appSn);
					List<UmsControllerResources> controllers = umsControllerResFacade.listUmsControllerRes(searchable);
					if(controllers.size()>0){
						UmsControllerResources controller = controllers.get(0);
						umsLog.setOpResource(controller.getMenu().getMenuName());
					}
				}
				Short logLevel = null;
				Short opResult = null;
				if(status == 200){
					logLevel = LogLevelEnum.NORMAL.getValue();
					opResult = OpResultEnum.SUCCESS.getValue();
				}else{
					logLevel = LogLevelEnum.MAJOR.getValue();
					opResult = OpResultEnum.FAIl.getValue();
				}
				umsLog.setLogLevel(logLevel);
				umsLog.setAppSn(appSn);
				umsLog.setUsername(username);
				umsLog.setLogType(logType);
				umsLog.setFrontEndAccessPath(frontEndAccessPath);
				umsLog.setIp(NetworkUtil.getIpAddress(request));
				umsLog.setBrowser(NetworkUtil.getBrowser(request));
				umsLog.setOpDesc(opDesc);
				umsLog.setExecTime(execTime);
				umsLog.setOpResult(opResult);
				umsLog.setOpSystem(NetworkUtil.getOS(request));
				umsLog.setOpTime(opTime);
				umsLog.setOpType((short)0);
				umsLogFacade.save(umsLog);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("记入日志信息失败");
			e.printStackTrace();
		}
		return result;
	}
}
