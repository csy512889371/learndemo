package com.ldap.service;

import com.ldap.model.rest.OrganizationRequest;

public interface OrganizationService {
   public String addOrganization(OrganizationRequest organizationRequest);
   public String delOrganization(String rdn);
}
