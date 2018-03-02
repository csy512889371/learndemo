package com.ctoedu.demo.api.service.auth;

import com.alibaba.fastjson.JSONObject;

public interface AuthService {

	JSONObject find(Object currentUsername, String appId, String menuId, String dataId, String pType, String rType);
}
