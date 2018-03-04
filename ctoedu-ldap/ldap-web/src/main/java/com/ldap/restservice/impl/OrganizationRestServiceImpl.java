package com.ldap.restservice.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldap.model.rest.OrganizationRequest;
import com.ldap.restservice.OrganizationRestService;
import com.ldap.service.OrganizationService;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

@Path("/usercenter/organization")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ ContentType.APPLICATION_JSON_UTF_8 })
@Service("organizationRestService")
public class OrganizationRestServiceImpl implements OrganizationRestService {

	@Autowired
	private OrganizationService organizationService;
	
	@POST
	@Path("create")
	public String addOrganization(OrganizationRequest organizationRequest) {
		return organizationService.addOrganization(organizationRequest);
	}
    
	@DELETE
	@Path("delete")
	public String delOrganization(@QueryParam("organization_name") String rdn) {
		return organizationService.delOrganization(rdn);
	}

}
