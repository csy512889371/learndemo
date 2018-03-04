package com.ldap.restservice.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.ldap.model.rest.OURequest;
import com.ldap.restservice.OURestService;
import com.ldap.service.OUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/usercenter/ou")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ ContentType.APPLICATION_JSON_UTF_8 })
@Service("oURestService")
public class OURestServiceImpl implements OURestService {

	@Autowired
	private OUService oUService;
	
	@POST
	@Path("create")
	public String addOU(OURequest oURequest) {
		return oUService.addOU(oURequest);
	}

	@DELETE
	@Path("delete")
	public String delOU(String rdn) {
		return oUService.delOU(rdn);
	}

}
