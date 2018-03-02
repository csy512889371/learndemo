package com.ctoedu.demo.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctoedu.demo.core.resource.service.UmsControllerOperService;
import com.ctoedu.demo.core.resource.service.UmsControllerResService;
import com.ctoedu.demo.core.user.service.UmsUserService;
import com.ctoedu.demo.facade.resource.entity.UmsControllerOper;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.test.base.BaseTest;

public class UmsControllerOperServiceTest extends BaseTest{
	
	@Autowired
	UmsControllerResService umsControllerResService;
	@Autowired
	UmsControllerOperService umsControllerOperService;
	@Autowired
	UmsUserService umsUserService;

	@Test
	public void assignControllerResOper(){
		UmsControllerResources resources = new UmsControllerResources();
		resources.setControllerName("1111");
		UmsControllerResources resources1 = umsControllerResService.save(resources);
		UmsControllerOper oper1 = new UmsControllerOper();
		oper1.setControllerOperName("aaa");
//		oper1.setControllerResources(resources1);
		UmsControllerOper oper2 = new UmsControllerOper();
		oper2.setControllerOperName("bbb");
//		oper2.setControllerResources(resources1);
		UmsControllerOper oper3 = new UmsControllerOper();
		oper3.setControllerOperName("ccc");
//		oper3.setControllerResources(resources1);
		UmsControllerOper oper4 = umsControllerOperService.save(oper1);
		oper4.setControllerResources(resources1);
		umsControllerOperService.update(oper4);
		UmsControllerOper oper5 = umsControllerOperService.save(oper2);
		oper5.setControllerResources(resources1);
		umsControllerOperService.update(oper5);
		UmsControllerOper oper6 = umsControllerOperService.save(oper3);
		oper6.setControllerResources(resources1);
		umsControllerOperService.update(oper6);
		resources1.setUmsControllerOpers(null);
		umsControllerResService.update(resources1);
	}
	
//	@Test
//	public void get(){
//		Set<UmsControllerResources> umsControllerRes =umsControllerResService.getControllerResByUrlAndUserAndAppSn("/api/app/findForPage", umsUserService.findByUsername("fay1234567"), "simba admin", AvailableEnum.TRUE.getValue());
//		System.out.println(umsControllerRes.size());
//	}
	
//	@Test
//	public void get(){
//		Set<UmsControllerResources> umsControllerRes =umsControllerResService.getControllerResByUserAndAppSn(umsUserService.findByUsername("fay1234567"), "simba admin", AvailableEnum.TRUE.getValue());
//		System.out.println(umsControllerRes.size());
//	}
}
