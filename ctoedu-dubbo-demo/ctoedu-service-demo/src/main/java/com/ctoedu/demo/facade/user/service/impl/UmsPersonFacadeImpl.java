package com.ctoedu.demo.facade.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.demo.core.user.service.UmsPersonService;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.service.UmsPersonFacade;

@Service(retries=2)
public class UmsPersonFacadeImpl implements UmsPersonFacade {
	
	@Autowired
	private UmsPersonService umsPersonService;

	@Override
	public UmsPerson updatePerson(UmsPerson person) {
		return umsPersonService.updatePerson(person);
	}

	@Override
	public UmsPerson findById(String id) {
		return umsPersonService.findOne(id);
	}
}
