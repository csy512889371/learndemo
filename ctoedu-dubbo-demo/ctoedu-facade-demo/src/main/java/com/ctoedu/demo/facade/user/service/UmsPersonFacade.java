package com.ctoedu.demo.facade.user.service;

import com.ctoedu.demo.facade.user.entity.UmsPerson;

/**
 * 
 * @author feichongzheng
 *
 */
public interface UmsPersonFacade {
	
	UmsPerson updatePerson(UmsPerson person);
	
	UmsPerson findById(String id);
}
