package com.ctoedu.demo.api.controller.common;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ListVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.controller.vo.common.AppForSelectVO;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
import com.ctoedu.demo.facade.position.service.UmsPositionFacade;
import com.ctoedu.demo.facade.resource.service.UmsMenuResFacade;

@Controller
@RequestMapping("/api/common")
public class CommonController {
	
	@Reference
	private UmsMenuResFacade umsMenuResFacade;
	
	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsPositionFacade umsPositionFacade;
	
	@RequestMapping(value="/findAppForSelect", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findAppForSelect(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsApp> appList = null;
		ListVO<AppForSelectVO> listVO = null;
		try {
			Searchable searchable = Searchable.newSearchable();
			//get all app by conditions
			appList = umsAppFacade.list(searchable);
			//convert to ListVO for view
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
}