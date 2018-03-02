package com.ctoedu.demo.test.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.department.service.UmsDepartmentService;
import com.ctoedu.demo.core.department.service.UmsUserDepartmentRelationService;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.test.base.BaseTest;
public class UmsDepartmentServiceTest extends BaseTest{
	@Autowired
	private UmsDepartmentService umsDepartmentService;
	@Autowired
	private UmsUserDepartmentRelationService umsUserDepartmentRelationService;
	@Test
	public void addDepartment(){
		UmsDepartment department1 = new UmsDepartment();
		department1.setName("开发平台");
		UmsDepartment department2 = umsDepartmentService.save(department1);
		assertEquals(department1.getName(), department2.getName());
	}
	
	@Test
	public void updateDepartment(){
		UmsDepartment department1 = new UmsDepartment();
		department1.setName("开发平台");
		UmsDepartment department2 = umsDepartmentService.save(department1);
		department2.setName("人脸点名");
		UmsDepartment department3 = umsDepartmentService.update(department2);
		assertEquals(department2.getName(), department3.getName());
	}
	
	@Test
	public void deleteDepartment(){
		UmsDepartment department = new UmsDepartment();
		department.setName("开发平台");
		UmsDepartment department1 = umsDepartmentService.save(department);
		umsDepartmentService.delete(department1);
		UmsDepartment department2 = umsDepartmentService.findOne(department.getId());
		Assert.isNull(department2);
	}
	
	@Test
	public void findAll(){
//		UmsDepartment department1 = new UmsDepartment();
//		department1.setName("开发平台2");
//		umsDepartmentService.save(department1);
//		UmsDepartment department2 = new UmsDepartment();
//		department2.setName("开发平台2");
//		umsDepartmentService.save(department2);
		Pageable page = new PageRequest(0, 200); 
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("name_like", "");
		searchParams.put("user.username_eq", "fay123456");
		Searchable searchable = Searchable.newSearchable(searchParams, page);
		Page<UmsUserDepartmentRelation> pages = umsUserDepartmentRelationService.findAll(searchable);
		for(UmsUserDepartmentRelation uugr : pages.getContent()){
			System.out.println(uugr.getDepartment().getName());
		}
	}
	
	@Test
	public void findUserByDepartmentId(){
		Pageable page = new PageRequest(0, 25);
		Page<UmsUser> pages = umsDepartmentService.findUserByDepartmentId("402882945a4b51fa015a4b52cf330006","爱死","", page);
		
		Assert.notNull(pages);
	}
	
	@Test
	public void findNotUserByDepartmentId(){
		Pageable page = new PageRequest(0, 25);
		Page<UmsUser> pages = umsDepartmentService.findNotUserByDepartmentId("402882945a4b51fa015a4b52cf330007", "", "", page);
		
		Assert.notNull(pages);
	}
	
	@Test
	public void clearUserDepartmentRelation(){
		List<String> list = new ArrayList<>();
		list.add("ff8080815b0d7517015b22e6aeb20000");
		list.add("402852425b0d74c3015b482e51a20000");
//		list.add("ff8080815b0d7517015b22e6aeb20000");
		umsDepartmentService.clearUserDepartmentRelation("402882945a4b51fa015a4b52d01d0008", list);
		Pageable page = new PageRequest(0, 25);
		Page<UmsUser> pages = umsDepartmentService.findUserByDepartmentId("402882945a4b51fa015a4b52d01d0008","","", page);
		System.err.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");
		for(UmsUser user : pages.getContent()){
			System.out.println(user.getId());
		}
		System.err.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");
	}
}
