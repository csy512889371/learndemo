package com.ctoedu.demo.facade.jwt.service;

import com.ctoedu.demo.facade.jwt.domain.Payload;

/**
 * JWT
 * @author feichongzheng
 *
 */
public interface UmsJwtFacade {
	
	String createJwt(String username, boolean remember) throws Exception;
	
	void removeJwt(String username, String jwt) throws Exception;
	
	Payload getPayload(String jwt);
}
