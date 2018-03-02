package com.ctoedu.demo.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import com.ctoedu.demo.core.app.service.UmsAppService;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.exception.AppSnExistsException;
import com.ctoedu.demo.test.base.BaseTest;
public class UmsAppServiceTest extends BaseTest{
	@Autowired
	private UmsAppService umsAppService;
	
//	@Autowired
//	private UmsRoleService umsRoleService;
//
//	@Autowired
//	private UmsControllerResService umsControllerResService;
//
//	@Autowired
//	private UmsMenuResService umsMenuResService;
	@Test
	public void addApp(){
		UmsApp app1 = new UmsApp();
		app1.setSn("Simba");
		app1.setName("开发平台");
		UmsApp app2 = umsAppService.saveApp(app1);
		assertEquals(app1.getName(), app2.getName());
	}
	
	@Test(expected=AppSnExistsException.class)
	public void addAppException(){
		UmsApp app1 = new UmsApp();
		app1.setSn("Simba1");
		app1.setName("开发平台1");
		umsAppService.saveApp(app1);
		UmsApp app2 = new UmsApp();
		app2.setSn("Simba1");
		app2.setName("开发平台1");
		umsAppService.saveApp(app1);
	}
	
	@Test
	public void updateApp(){
		UmsApp app1 = new UmsApp();
		app1.setSn("Simba");
		app1.setName("开发平台");
		UmsApp app2 = umsAppService.saveApp(app1);
		app2.setName("人脸点名");
		UmsApp app3 = umsAppService.update(app2);
		assertEquals(app2.getName(), app3.getName());
	}
	
	@Test
	public void deleteApp(){
		UmsApp app = new UmsApp();
		app.setSn("Simba");
		app.setName("开发平台");
		UmsApp app1 = umsAppService.saveApp(app);
		umsAppService.delete(app1);
		UmsApp app2 = umsAppService.findAppBySn(app.getSn());
		Assert.isNull(app2);
	}
	
//	@Test
//	public void delete(){
//		List<String> ids = new ArrayList<>();
//		ids.add("ff80808159aa70380159aa795cfe0000");
//		umsRoleService.deleteByAppIdIn(ids);
//		umsControllerResService.deleteByAppIds(ids);
//		umsMenuResService.deleteByAppIds(ids);
//		umsAppService.delete(ids.toArray(new String[ids.size()]));
//	}
	
	@Test
	public void findAll(){
//		UmsApp app1 = new UmsApp();
//		app1.setSn("Simba11");
//		app1.setName("开发平台2");
//		umsAppService.saveApp(app1);
//		UmsApp app2 = new UmsApp();
//		app2.setSn("Simba22");
//		app2.setName("开发平台2");
//		umsAppService.saveApp(app2);
		Pageable page = new PageRequest(0, 20); 
//		Map<String, Object> searchParams = new HashMap<String, Object>();
//		searchParams.put("name", "开发平台2");
//		Searchable searchable = Searchable.newSearchable(searchParams, page);
//		Page<UmsApp> pages = umsAppService.findAll(searchable);
		Page<UmsApp> pages = umsAppService.listPage("", "ff808081596c857901596caa1a980000", page);
		Assert.notNull(pages);
	}
}
