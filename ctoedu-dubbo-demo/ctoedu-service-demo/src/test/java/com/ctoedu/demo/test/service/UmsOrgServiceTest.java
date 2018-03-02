package com.ctoedu.demo.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.org.service.UmsUserOrgRelationService;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.test.base.BaseTest;

public class UmsOrgServiceTest extends BaseTest {
	
	@Autowired
	private UmsUserOrgRelationService umsUserOrgRelationService;
	
//	@Autowired
//	private UmsOrgService umsOrgService;
	
	@Test
	public void find(){
		Pageable page = new PageRequest(0, 10);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSort(Direction.ASC, "org.orderNum");
		searchable.addSearchParam("org.name_like", "");
		searchable.addSearchParam("user.username_eq", "fay123456");
		Page<UmsUserOrgRelation> uuors = umsUserOrgRelationService.findAll(searchable);
		for(UmsUserOrgRelation r:uuors.getContent()){
			System.out.println(r.getOrg().getName());
		}
	}
	
//	@Test
//	public void delete(){
//		umsOrgService.deleteOrg("ff8080815d3c4092015d9cf5cea20002");
//	}
}
