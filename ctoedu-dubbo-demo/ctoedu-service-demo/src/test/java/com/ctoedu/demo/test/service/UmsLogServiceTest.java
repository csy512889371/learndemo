package com.ctoedu.demo.test.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.utils.DateUtil;
import com.ctoedu.demo.core.log.service.UmsLogService;
import com.ctoedu.demo.facade.log.entity.UmsLog;
import com.ctoedu.demo.facade.log.enums.LogTypeEnum;
import com.ctoedu.demo.test.base.BaseTest;
public class UmsLogServiceTest extends BaseTest{
	
	@Autowired
	private UmsLogService umsLogService;
	
	@Test
	public void add(){
		UmsLog log = new UmsLog();
		log.setOpResource("123");
		log.setLogLevel((short)1);
		log.setAppSn("simba admin");
		log.setUsername("123");
		log.setLogType(LogTypeEnum.INTERFACE.getValue());
		log.setFrontEndAccessPath("1111");
		log.setIp("1121231312");
		log.setBrowser("131231231232131213");
//		log.setBackEndAccessPath(backEndAccessPath);
		log.setOpDesc("22222222");
		log.setExecTime(123l);
		log.setOpResult((short)0);
		log.setOpSystem("mac");
		log.setOpTime(DateUtil.utilDateToLocalDateTime(new Date()));
		log.setOpType((short)0);
		log = umsLogService.save(log);
		System.out.println(JSONObject.toJSONString(log));
	}
}
