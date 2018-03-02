package com.ctoedu.demo.test.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.role.service.UmsRoleService;
import com.ctoedu.demo.core.role.service.UmsUserRoleRelationService;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.test.base.BaseTest;

public class UmsRoleServiceTest extends BaseTest {
	@Autowired
	private UmsRoleService umsRoleService;
	@Autowired
	private UmsUserRoleRelationService umsUserRoleRelationService;
	@Test
	public void addRole(){
		UmsRole role1 = new UmsRole();
		role1.setSn("RJGCS");
		role1.setName("软件工程师");
		UmsRole role2 = umsRoleService.saveRole(role1);
		assertEquals(role1.getName(), role2.getName());
	}
	
//	@Test
//	public void addRoleException(){
//		UmsRole role1 = new UmsRole();
//		role1.setSn("RJGCS1");
//		role1.setName("软件工程师1");
//		umsRoleService.saveRole(role1);
//		UmsRole role2 = new UmsRole();
//		role2.setSn("RJGCS1");
//		role2.setName("软件工程师1");
//		umsRoleService.saveRole(role2);
//	}
	
	@Test
	public void updateRole(){
		UmsRole role1 = new UmsRole();
		role1.setSn("RJGCS");
		role1.setName("软件工程师");
		UmsRole role2 = umsRoleService.saveRole(role1);
		role2.setName("人脸点名");
		UmsRole role3 = umsRoleService.update(role2);
		assertEquals(role2.getName(), role3.getName());
	}
	
	@Test
	public void deleteRole(){
		UmsRole role = new UmsRole();
		role.setSn("RJGCS");
		role.setName("软件工程师");
		UmsRole role1 = umsRoleService.saveRole(role);
		umsRoleService.delete(role1);
		UmsRole role2 = umsRoleService.findByRoleSn(role.getSn());
		Assert.isNull(role2);
	}
	
	@Test
	public void findAll(){
//		UmsRole role1 = new UmsRole();
//		role1.setSn("RJGCS11");
//		role1.setName("软件工程师2");
//		umsRoleService.saveRole(role1);
//		UmsRole role2 = new UmsRole();
//		role2.setSn("RJGCS22");
//		role2.setName("软件工程师2");
//		umsRoleService.saveRole(role2);
		Pageable page = new PageRequest(0, 20); 
		Map<String, Object> searchParams = new HashMap<String, Object>();
//		searchParams.put("name", "软件工程师2");
		searchParams.put("user.username_eq", "fay123456");
		Searchable searchable = Searchable.newSearchable(searchParams, page);
		Page<UmsUserRoleRelation> pages = umsUserRoleRelationService.findAll(searchable);
		for(UmsUserRoleRelation r : pages.getContent()){
			System.out.println(r.getRole().getName());
		}
		Assert.notNull(pages);
	}
}
