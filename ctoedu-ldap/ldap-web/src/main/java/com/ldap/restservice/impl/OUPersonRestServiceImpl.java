package com.ldap.restservice.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.ldap.model.rest.*;
import com.ldap.restservice.OUPersonRestService;
import com.ldap.service.OUPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/usercenter/ouperson")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ ContentType.APPLICATION_JSON_UTF_8 })
@Service("oUPersonRestService")
public class OUPersonRestServiceImpl implements OUPersonRestService {
	@Autowired
	private OUPersonService oUPersonService;
	
	@POST
	@Path("create")
	public String addOUPerson(OUPersonRequest oUPersonRequest) {
		return oUPersonService.addOUPerson(oUPersonRequest);
	}

	@DELETE
	@Path("delete")
	public String delOUPerson(String rdn) {
		return oUPersonService.delOUPerson(rdn);
	}

	@POST
	@Path("register")
	public String addOURegister(OURegisterRequest oURegisterRequest) {
		return oUPersonService.addOURegister(oURegisterRequest);
	}
    
	@GET
	@Path("account")
	public OUPersonResponse getOUPerson(@QueryParam("login_name") String loginName,@QueryParam("user_password") String userPassword) {
		return oUPersonService.getOUPerson(loginName, userPassword);
	}

	@GET
	@Path("move")
	public String moveOUPerson(@QueryParam("oldrdn") String oldrdn,@QueryParam("newrdn")  String newrdn) {
		return oUPersonService.moveOUPerson(oldrdn, newrdn);
	}

	@POST
	@Path("hierarchy")
	public String addOUHierarchy(OUHierarchyRequest oUHierarchyRequest) {
		return oUPersonService.addOUHierarchy(oUHierarchyRequest);
	}

	@GET
	@Path("rdn")
	public String getRdn(@QueryParam("login_name") String uid) {
		return oUPersonService.getRdn(uid);
	}

	@GET
	@Path("nexthierarchy")
	public List<OUHierarchyResponse> getNextOUHierarchy(@QueryParam("rdn") String rdn) {
		return oUPersonService.getNextOUHierarchy(rdn);
	}

	@DELETE
	@Path("delhierarchy")
	public String delOUHierarchy(@QueryParam("rdn") String rdn) {
		return oUPersonService.delOUHierarchy(rdn);
	}
}
