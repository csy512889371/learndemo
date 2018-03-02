package com.ctoedu.demo.facade.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.demo.core.jwt.service.UmsJwtService;
import com.ctoedu.demo.facade.jwt.domain.Payload;
import com.ctoedu.demo.facade.jwt.service.UmsJwtFacade;

@Service(retries=2)
public class UmsJwtFacadeImpl implements UmsJwtFacade {
	
	@Autowired
	private UmsJwtService umsJwtService;
	
	@Override
	public String createJwt(String username, boolean remember) throws Exception {
		return umsJwtService.createJwt(username, remember);
	}
	
	@Override
	public void removeJwt(String username, String jwt) throws Exception {
		umsJwtService.removeJwt(username, jwt);
	}

	@Override
	public Payload getPayload(String jwt) {
		return umsJwtService.getPayload(jwt);
	}
}
