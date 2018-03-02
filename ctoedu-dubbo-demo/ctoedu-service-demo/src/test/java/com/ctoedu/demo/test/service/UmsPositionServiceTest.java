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
import com.ctoedu.demo.core.position.service.UmsPositionService;
import com.ctoedu.demo.core.position.service.UmsUserPositionRelationService;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.position.exception.PositionSnExistsException;
import com.ctoedu.demo.test.base.BaseTest;

public class UmsPositionServiceTest extends BaseTest {
	@Autowired
	private UmsPositionService umsPositionService;
	@Autowired
	private UmsUserPositionRelationService umsUserPositionRelationService;
	@Test
	public void addPosition(){
		UmsPosition position1 = new UmsPosition();
		position1.setSn("RJGCS2");
		position1.setName("软件工程师");
		UmsPosition position2 = umsPositionService.savePosition(position1);
		assertEquals(position1.getName(), position2.getName());
	}
	
	@Test(expected=PositionSnExistsException.class)
	public void addPositionException(){
		UmsPosition position1 = new UmsPosition();
		position1.setSn("RJGCS3");
		position1.setName("软件工程师1");
		umsPositionService.savePosition(position1);
		UmsPosition position2 = new UmsPosition();
		position2.setSn("RJGCS3");
		position2.setName("软件工程师1");
		umsPositionService.savePosition(position2);
	}
	
	@Test
	public void updatePosition(){
		UmsPosition position1 = new UmsPosition();
		position1.setSn("RJGCS");
		position1.setName("软件工程师");
		UmsPosition position2 = umsPositionService.savePosition(position1);
		position2.setName("人脸点名");
		UmsPosition position3 = umsPositionService.update(position2);
		assertEquals(position2.getName(), position3.getName());
	}
	
	@Test
	public void deletePosition(){
		UmsPosition position = new UmsPosition();
		position.setSn("RJGCS");
		position.setName("软件工程师");
		UmsPosition position1 = umsPositionService.savePosition(position);
		umsPositionService.delete(position1);
		UmsPosition position2 = umsPositionService.findBySn(position.getSn());
		Assert.isNull(position2);
	}
	
	@Test
	public void findAll(){
//		UmsPosition position1 = new UmsPosition();
//		position1.setSn("RJGCS11");
//		position1.setName("软件工程师2");
//		umsPositionService.savePosition(position1);
//		UmsPosition position2 = new UmsPosition();
//		position2.setSn("RJGCS22");
//		position2.setName("软件工程师2");
//		umsPositionService.savePosition(position2);
		Pageable page = new PageRequest(0, 20); 
		Map<String, Object> searchParams = new HashMap<String, Object>();
//		searchParams.put("name", "软件工程师2");
		searchParams.put("user.username_eq", "fay123456");
		Searchable searchable = Searchable.newSearchable(searchParams, page);
		Page<UmsUserPositionRelation> pages = umsUserPositionRelationService.findAll(searchable);
		for(UmsUserPositionRelation uupr : pages.getContent()){
			System.out.println(uupr.getPosition().getName());
		}
		Assert.notNull(pages);
	}
}
