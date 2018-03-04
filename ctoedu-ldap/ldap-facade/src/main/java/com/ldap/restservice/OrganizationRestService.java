package com.ldap.restservice;

import com.ldap.model.rest.OrganizationRequest;

public interface OrganizationRestService {
	 public String addOrganization(OrganizationRequest organizationRequest);
	 public String delOrganization(String rdn);
}
